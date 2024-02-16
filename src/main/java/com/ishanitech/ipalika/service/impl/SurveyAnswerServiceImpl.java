package com.ishanitech.ipalika.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.jdbi.v3.core.JdbiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ishanitech.ipalika.config.properties.RestBaseProperty;
import com.ishanitech.ipalika.converter.impl.AnswerConverter;
import com.ishanitech.ipalika.converter.impl.SurveyAnswerConverter;
import com.ishanitech.ipalika.dao.DistrictDAO;
import com.ishanitech.ipalika.dao.SurveyAnswerDAO;
import com.ishanitech.ipalika.dao.UserDAO;
import com.ishanitech.ipalika.dto.AnswerDTO;
import com.ishanitech.ipalika.dto.FamilyMemberDTO;
import com.ishanitech.ipalika.dto.PaginationTypeClass;
import com.ishanitech.ipalika.dto.RequestDTO;
import com.ishanitech.ipalika.dto.ResidentDTO;
import com.ishanitech.ipalika.dto.ResidentDetailDTO;
import com.ishanitech.ipalika.dto.RoleWardDTO;
import com.ishanitech.ipalika.dto.SurveyAnswerDTO;
import com.ishanitech.ipalika.dto.SurveyAnswerExtraInfoDTO;
import com.ishanitech.ipalika.dto.UserDTO;
import com.ishanitech.ipalika.exception.CustomSqlException;
import com.ishanitech.ipalika.exception.FileStorageException;
import com.ishanitech.ipalika.model.Answer;
import com.ishanitech.ipalika.model.QuestionOption;
import com.ishanitech.ipalika.model.SurveyAnswer;
import com.ishanitech.ipalika.security.CustomUserDetails;
import com.ishanitech.ipalika.service.DbService;
import com.ishanitech.ipalika.service.ResidentService;
import com.ishanitech.ipalika.service.SurveyAnswerService;
import com.ishanitech.ipalika.utils.CustomQueryCreator;
import com.ishanitech.ipalika.utils.FileUtilService;
import com.ishanitech.ipalika.utils.ImageUtilService;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code SurveyAnswerServiceImpl} is an implementation class of {@code SurveyAnswerServie} interface, which implements
 * all the business logics related to survey and it's answers. 
 * @author <b> Umesh Bhujel
 * @since 1.0
 */

@Slf4j
@Service
public class SurveyAnswerServiceImpl implements SurveyAnswerService {
	private final DbService dbService;
	private final FileUtilService fileUtilService;
	private final ResidentService residentService;
	
	@Autowired
	private RestBaseProperty restUrlProperty;
	
	public SurveyAnswerServiceImpl(DbService dbService, FileUtilService fileUtilService, ResidentService residentService) {
		this.dbService = dbService;
		this.fileUtilService = fileUtilService;
		this.residentService = residentService;
	}

	@Override
	public void addSurveyAnswers(RequestDTO<List<SurveyAnswerDTO>, List<SurveyAnswerExtraInfoDTO>> surveyAnswerInfo) {
		SurveyAnswerConverter surveyAnswerConverter = new SurveyAnswerConverter();
		List<String> filledIdsInDatabase = dbService.getDao(SurveyAnswerDAO.class).getAllFilledIds();
		List<SurveyAnswer> surveyAnswers = surveyAnswerConverter
				.fromDto(surveyAnswerInfo.getData())
				.stream()
				.filter(surveyAns -> !filledIdsInDatabase.contains(surveyAns.getFilledId()))
				.collect(Collectors.toList());
		List<SurveyAnswerExtraInfoDTO> surveyExtraInfo = surveyAnswerInfo.getInfoData().stream()
				.filter(extraData -> !filledIdsInDatabase.contains(extraData.getFilledFormId()))
				.collect(Collectors.toList());
		try {
			List<Answer> answers = surveyAnswerConverter.fromSuveyAnswersToAnswersList(surveyAnswers);
			IntStream.range(0, answers.size())
				.forEach(i -> {
					answers.get(i).setAddedBy(surveyExtraInfo.get(i).getUserId());
					answers.get(i).setDuration(surveyExtraInfo.get(i).getDuration());
					answers.get(i).setEntryDate(String.format("%d-%d-%d", surveyExtraInfo.get(i).getDate().getDate(), surveyExtraInfo.get(i).getDate().getDay(), surveyExtraInfo.get(i).getDate().getMonth()));
				});
			
			dbService.getDao(SurveyAnswerDAO.class).addAnswerList(answers);
		} catch(JdbiException jex) {
			throw new CustomSqlException("Exception: " + jex.getMessage());
		}
	}
	
	@Override
	public void addSurveyAnswerImage(MultipartFile image) throws FileStorageException {
		fileUtilService.storeFile(image);
	}
	
	@Override
	public void updateSurveyAnswerImage(MultipartFile image) {
		fileUtilService.storeEditedFile(image);
	}

	/**
	 * Adds answer to answer table.
	 * Answer table is the latest table which replaces the old survey_answer table.
	 */
	@Override
	public void addAnswers(List<AnswerDTO> answersRequest) {
		List<String> filledIdsInDatabase = dbService.getDao(SurveyAnswerDAO.class).getAllFilledIds();
		List<Answer> answers = new AnswerConverter().fromDto(answersRequest)
				.stream()
				.filter(answer -> !filledIdsInDatabase.contains(answer.getFilledId()))
				.collect(Collectors.toList());
		
		try {
			dbService.getDao(SurveyAnswerDAO.class).addAnswerList(answers);
		} catch(JdbiException jex) {
			throw new CustomSqlException("Exception: " + jex.getMessage());
		}
	}
	
	@Override
	public void updateAnswers(AnswerDTO answer) {
		Answer answerModel = new AnswerConverter().fromDto(answer);
		String filledId = answer.getFilledId();
		
		try {
			dbService.getDao(SurveyAnswerDAO.class).updateSurveyAnswer(answerModel);
		}catch(JdbiException jex) {
			throw new CustomSqlException("Exception: " + jex.getMessage());
		}
		
	}
	


	@Override
	public List<ResidentDTO> getResident(RoleWardDTO roleWardDTO, HttpServletRequest request) {
		try {
			List<ResidentDTO> residents;
			
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			UserDTO loggedInUser = userDetails.getUser();
			
			String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.RESIDENTS);
			
			//For Ward Admin
			if(roleWardDTO.getRole() == 3) {
				residents = dbService.getDao(SurveyAnswerDAO.class).searchResidentByWard(Integer.toString(roleWardDTO.getWardNumber()), caseQuery);
			} 
			
			//For Surveyor
			else if(loggedInUser.getRoles().contains("SURVEYOR")) {
				String adjustedCaseQuery = " AND a.added_by = " + loggedInUser.getUserId() + caseQuery;
				log.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$" + adjustedCaseQuery);
				residents = dbService.getDao(SurveyAnswerDAO.class).getResidents(adjustedCaseQuery);
			}
			
			//For other
			else {
			residents = dbService.getDao(SurveyAnswerDAO.class).getResidents(caseQuery);
			}
			residents.forEach(resident -> resident.setImageUrl(ImageUtilService.makeFullResizedImageurl(restUrlProperty, resident.getImageUrl())));
			return residents;
		} catch(JdbiException jex) {
			throw new CustomSqlException("Exception: " + jex.getLocalizedMessage());
		}
	}

	@Override
	public Answer getRawAnswerByFilledId(String filledId) {
		try {
			ResidentDetailDTO residentDetail = new ResidentDetailDTO();
			String surveyor = ""; 
			List<QuestionOption> questionOption = dbService.getDao(SurveyAnswerDAO.class).getAllQuestionWithOptions();
			List<Integer> questionWithOptions = questionOption.stream().map(questionOpt -> questionOpt.getQuestionId()).collect(Collectors.toList());
			Answer answer = dbService.getDao(SurveyAnswerDAO.class).getAnswerByFilledId(filledId);
			surveyor = dbService.getDao(UserDAO.class).getUserFullNameById(answer.getAddedBy());
			residentDetail.setSurveyor(surveyor);
			
			//Setting the image of the houseowner and the document
			answer.setAnswer46(ImageUtilService.makeFullImageurl(restUrlProperty, answer.getAnswer46()));
			answer.setAnswer47(ImageUtilService.makeFullImageurl(restUrlProperty, answer.getAnswer47()));
			answer.setAnswer48(ImageUtilService.makeFullImageurl(restUrlProperty, answer.getAnswer48()));
			answer.setAnswer49(ImageUtilService.makeFullImageurl(restUrlProperty, answer.getAnswer49()));
			answer.setAnswer51(ImageUtilService.makeFullImageurl(restUrlProperty, answer.getAnswer51()));
			return answer;
		} catch(JdbiException jex) {
			throw new CustomSqlException("Exception: " + jex.getLocalizedMessage());
		}
	}
	
	@Override
	public ResidentDetailDTO getAnswerByFilledId(String filledId) {
		try {
			ResidentDetailDTO residentDetail = new ResidentDetailDTO();
			String surveyor = ""; 
			List<QuestionOption> questionOption = dbService.getDao(SurveyAnswerDAO.class).getAllQuestionWithOptions();
			List<Integer> questionWithOptions = questionOption.stream().map(questionOpt -> questionOpt.getQuestionId()).collect(Collectors.toList());
			Answer answer = dbService.getDao(SurveyAnswerDAO.class).getAnswerByFilledId(filledId);
			surveyor = dbService.getDao(UserDAO.class).getUserFullNameById(answer.getAddedBy());
			residentDetail.setSurveyor(surveyor);
			List<FamilyMemberDTO> familyMembers = residentService.getAllFamilyMembersFromFamilyId(filledId);
			residentDetail.setFamilyMembers(familyMembers);
			IntStream.rangeClosed(1, 100).forEach(i -> {
				if(questionWithOptions.contains(i)) {
					try {
						Method getAnswerMethod = Answer.class.getMethod(String.format("getAnswer%d", i));
						String ans = extractAnswer(getAnswerMethod.invoke(answer), questionOption.get(questionWithOptions.indexOf(i)));
						Method setAnswerMethod = answer.getClass().getMethod(String.format("setAnswer%d", i), String.class);
						setAnswerMethod.invoke(answer, ans);
					} catch (NoSuchMethodException e) {
						log.info(String.format("Error : %s", e.getMessage()));
					} catch (SecurityException e) {
						log.info(String.format("Error : %s", e.getMessage()));
					} catch (IllegalAccessException e) {
						log.info(String.format("Error : %s", e.getMessage()));
					} catch (IllegalArgumentException e) {
						log.info(String.format("Error : %s", e.getMessage()));
					} catch (InvocationTargetException e) {
						log.info(String.format("Error : %s", e.getMessage()));
					}
				}
			});
			
			//Setting the district of the resident manually
			answer.setAnswer12(dbService.getDao(DistrictDAO.class).getDistrictNameNepaliByDistrictId(Integer.parseInt(answer.getAnswer12())));
			//Setting the image of the houseowner and the document
			answer.setAnswer46(ImageUtilService.makeFullImageurl(restUrlProperty, answer.getAnswer46()));
			answer.setAnswer47(ImageUtilService.makeFullImageurl(restUrlProperty, answer.getAnswer47()));
			answer.setAnswer48(ImageUtilService.makeFullImageurl(restUrlProperty, answer.getAnswer48()));
			answer.setAnswer49(ImageUtilService.makeFullImageurl(restUrlProperty, answer.getAnswer49()));
			answer.setAnswer51(ImageUtilService.makeFullImageurl(restUrlProperty, answer.getAnswer51()));
			residentDetail.setResidentDetail(answer);
			return residentDetail;
		} catch(JdbiException jex) {
			throw new CustomSqlException("Exception: " +jex.getLocalizedMessage());
		}
	}

	/**
	 * @param rawAnswer String 
	 * @param questionOption 
	 * @return String in Nepali form.
	 */
	private String extractAnswer(Object rawAnswer, QuestionOption questionOption) {
		String rawanswer = rawAnswer.toString();
		log.info(rawanswer);
		switch(questionOption.getQuestionType()) {
		case CHECKBOX:
			if(rawanswer.contains(",")) {
				return replaceArrayBracket(Stream.of(rawanswer.split(","))
						.map(option -> {
							return questionOption.getOptions().get(Integer.parseInt(option)).getOptionText();
						})
						.collect(Collectors.toList()).toString());
			} else {
				return questionOption.getOptions().get(Integer.parseInt(rawanswer)).getOptionText();
			}
			
			
		case CHECKBOX_N:
			if(rawanswer.contains(",")) {
				String[] extractedData = rawanswer.split(",");
				List<String> answer = Stream.of(extractedData)
					.map(keyValueData -> {
						String[] extractedPair = keyValueData.split(":");
						return questionOption.getOptions().get(Integer.parseInt(extractedPair[0])).getOptionText()
								+ "(" + extractedPair[1] + ")";
					}).collect(Collectors.toList());
				return replaceArrayBracket(answer.toString());
			} else {
				String[] extractedData = rawanswer.split(":");
	 			return questionOption.getOptions().get(Integer.parseInt(extractedData[0])).getOptionText() + "(" + extractedData[1] +")";
			}
			
			
		case RADIO:
				return questionOption.getOptions().get(Integer.parseInt(rawanswer)).getOptionText();
				
		case RADIO_D:
			if(rawanswer.contains(":")) {
				String[] extractedData = rawanswer.split(":");
				return questionOption.getOptions().get(Integer.parseInt(extractedData[0])).getOptionText() + "(" + extractedData[1] + ")";
			}
			
			return questionOption.getOptions().get(Integer.parseInt(rawanswer)).getOptionText();
			
		case RATING_M:
			if(rawanswer.contains(",")) {
				String[] extractedData = rawanswer.split(",");
				List<String> options = new ArrayList<>();
				IntStream.range(0, extractedData.length)
				.forEach(i -> {
					String s = questionOption.getOptions().get(i).getOptionText() + "(" + extractedData[i] +")";
					options.add(s);
				});
				return replaceArrayBracket(options.toString());
			} else {
				return questionOption.getOptions().get(0).getOptionText() + "(" + rawanswer +")";
			}
			
		case DROPDOWN:
			return questionOption.getOptions().get(Integer.parseInt(rawanswer)).getOptionText();
			
		case RADIO_M:
			if(rawanswer.contains(",")) {
				String[] extractedData = rawanswer.split(",");
				List<String> options = new ArrayList<>();
				IntStream.range(0, extractedData.length)
				.forEach(i -> {
					String s = questionOption.getOptions().get(i).getOptionText() + "(" + extractedData[i] +")";
					options.add(s);
				});
				return replaceArrayBracket(options.toString());
			} else {
				return questionOption.getOptions().get(0).getOptionText() + "(" + rawanswer +")";
			}
		
		default:
			return "";
		}
	}
	
	private String replaceArrayBracket(String rawString) {
		return rawString.replace("[", "").replace("]", "");
	}

	@Override
	public List<ResidentDTO> searchResident(HttpServletRequest request, String searchKey, String wardNo) {
		String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.RESIDENTS);
		List<ResidentDTO> residents;
		
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserDTO loggedInUser = userDetails.getUser();
		
		
		if(loggedInUser.getRoles().contains("SURVEYOR")) {
			log.info("Surveyor called" + "####################" + request.toString());
			String adjustedCaseQuery = " AND a.added_by = " + loggedInUser.getUserId() + caseQuery;
			residents = dbService.getDao(SurveyAnswerDAO.class).searchAllResidentByKey(searchKey, adjustedCaseQuery);
		} 
		
		else if(wardNo.equals("")) {
			log.info("All search called");
			residents = dbService.getDao(SurveyAnswerDAO.class).searchAllResidentByKey(searchKey, caseQuery);
		} 

		else {
			log.info("Super admin  called");
			residents = dbService.getDao(SurveyAnswerDAO.class).searchResidentByKey(searchKey, wardNo, caseQuery);
		}
		residents.forEach(resident -> {
			resident.setImageUrl(ImageUtilService.makeFullResizedImageurl(restUrlProperty, resident.getImageUrl()));
		});
		return residents;
	}

	@Override
	public List<ResidentDTO> searchWardResident(String wardNo, HttpServletRequest request) {
		
		String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.RESIDENTS);
		List<ResidentDTO> residents;
		if(wardNo.equals("")) {
			residents = dbService.getDao(SurveyAnswerDAO.class).searchAllResidentByWard(caseQuery);
		}else {
		residents = dbService.getDao(SurveyAnswerDAO.class).searchResidentByWard(wardNo, caseQuery);
		}
		residents.forEach(resident -> {
			resident.setImageUrl(ImageUtilService.makeFullResizedImageurl(restUrlProperty, resident.getImageUrl()));
		});
		return residents;
	}
	
	

	@Override
	public List<ResidentDTO> searchToleResident(String wardNo, HttpServletRequest request) {
		String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.RESIDENTS);
		List<ResidentDTO> residents;
		if(wardNo.equals("")) {
			residents = dbService.getDao(SurveyAnswerDAO.class).searchAllResidentByWard(caseQuery);
		}else {
		residents = dbService.getDao(SurveyAnswerDAO.class).searchResidentByWard(wardNo, caseQuery);
		}
		residents.forEach(resident -> {
			resident.setImageUrl(ImageUtilService.makeFullResizedImageurl(restUrlProperty, resident.getImageUrl()));
		});
		return residents;
	}


	@Override
	public List<ResidentDTO> getNextLotResident(RoleWardDTO roleWardDTO, HttpServletRequest request) {
		try {
			List<ResidentDTO> residents;
			String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.RESIDENTS);
			
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			UserDTO loggedInUser = userDetails.getUser();
			
			if(loggedInUser.getRoles().contains("SURVEYOR")) {
				String adjustedCaseQuery = " AND a.added_by = " + loggedInUser.getUserId() + caseQuery;
				log.info("CaseQuerySurveyor--->"+ adjustedCaseQuery);
				residents = dbService.getDao(SurveyAnswerDAO.class).getResidents(adjustedCaseQuery);
			} 
			
			else if(roleWardDTO.getRole() == 3) {
				log.info("CaseQueryWardAdmin--->"+ caseQuery);
				residents = dbService.getDao(SurveyAnswerDAO.class).searchResidentByWard(Integer.toString(roleWardDTO.getWardNumber()), caseQuery);	
			} 
			
			else {
				log.info("CaseQueryCentralAdmin--->"+ caseQuery);
				residents = dbService.getDao(SurveyAnswerDAO.class).getResidents(caseQuery);
			}

			residents.forEach(resident -> resident.setImageUrl(ImageUtilService.makeFullResizedImageurl(restUrlProperty, resident.getImageUrl())));
			
			if(request.getParameter("action").equals("prev")) {
				
				if(request.getParameter("sortBy") == null) {
				List<ResidentDTO> orderedresidents = reverseList(residents);
				residents = orderedresidents;
				}
			}
			
			return residents;
		} catch(JdbiException jex) {
			throw new CustomSqlException("Exception: " +jex.getLocalizedMessage());
		}
	}
	
	  public static<T> List<T> reverseList(List<T> list)
	  {
	    List<T> reverse = new ArrayList<>(list);
	    Collections.reverse(reverse);
	    return reverse;
	  }

	@Override
	public List<ResidentDTO> getWardResidentByPageLimit(HttpServletRequest request) {
		String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.RESIDENTS);
		
		List<ResidentDTO> residents;
		
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserDTO loggedInUser = userDetails.getUser();
		
		if(loggedInUser.getRoles().contains("SURVEYOR")) {
			String adjustedCaseQuery = " AND a.added_by = " + loggedInUser.getUserId() + caseQuery;
			residents = dbService.getDao(SurveyAnswerDAO.class).getResidents(adjustedCaseQuery);
		} else {
			System.out.println("case Query-->" + caseQuery);
			residents = dbService.getDao(SurveyAnswerDAO.class).getResidents(caseQuery);
		}
		
		residents.forEach(resident -> {
			resident.setImageUrl(ImageUtilService.makeFullResizedImageurl(restUrlProperty, resident.getImageUrl()));
		});
		return residents;
	}

	@Override
	public List<ResidentDTO> getSortedResident(HttpServletRequest request) {
		String caseQuery = CustomQueryCreator.generateQueryWithCase(request, PaginationTypeClass.RESIDENTS);
		
		List<ResidentDTO> residents;
		
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserDTO loggedInUser = userDetails.getUser();
		
		if(loggedInUser.getRoles().contains("SURVEYOR")) {
			String adjustedCaseQuery = " AND a.added_by = " + loggedInUser.getUserId() + caseQuery;
			residents = dbService.getDao(SurveyAnswerDAO.class).getResidents(adjustedCaseQuery);
		} else {
			residents = dbService.getDao(SurveyAnswerDAO.class).getResidents(caseQuery);
		}
		
		residents.forEach(resident -> {
			resident.setImageUrl(ImageUtilService.makeFullResizedImageurl(restUrlProperty, resident.getImageUrl()));
		});
		return residents;
	}


}
