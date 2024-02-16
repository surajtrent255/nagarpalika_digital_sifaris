package com.ishanitech.ipalika.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;

import com.ishanitech.ipalika.dto.PaginationTypeClass;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomQueryCreator {

    private static final Integer LAST_SEEN_ID = 0;
    private static final Integer PAGE_SIZE = 10;
    private static final String ACTION = "first";
    private static final String SEARCH_KEY = "";
    private static final String SORT_BY = "";
    private static final String SORT_BY_ORDER = "";
    
	// Generates the dynamic query based on filters and query parameters
    public static String generateQueryWithCase(HttpServletRequest request, PaginationTypeClass ptc) {
        String caseQuery = "";
        Integer lastSeenId;
        String action;
        Integer pageSize;
        String searchKey;
        String wardNo;
        String tole;
        String sortBy;
        String sortByOrder;
        String currentPageNo;
        String placeType;
        String surveyor;
        int currentPage;
        
        if (checkParameter("last_seen", request)) {
            lastSeenId = Integer.parseInt(getParameterFromRequestObject("last_seen", request));
        } else {
            lastSeenId = LAST_SEEN_ID;
        }
        
        if (checkParameter("action", request)) {
            action = (String) getParameterFromRequestObject("action", request);
        } else {
            action = ACTION;
        }
        
        if (checkParameter("pageSize", request)) {
            pageSize = Integer.parseInt(getParameterFromRequestObject("pageSize", request));
        } else {
            pageSize = PAGE_SIZE;
        }

        if (checkParameter("searchKey", request)) {
            searchKey = (String) getParameterFromRequestObject("searchKey", request);
        } else {
            searchKey = SEARCH_KEY;
        }
        
        if (checkParameter("wardNo", request)) {
            wardNo = (String) getParameterFromRequestObject("wardNo", request);
        } else {
            wardNo = "";
        }
        
        if (checkParameter("toleName", request)) {
            tole = (String) getParameterFromRequestObject("toleName", request);
        } else {
            tole = "";
        }
        
        if (checkParameter("placeType", request)) {
            placeType = (String) getParameterFromRequestObject("placeType", request);
        } else {
            placeType = "";
        }
        
        if (checkParameter("surveyor", request)) {
        	surveyor = (String) getParameterFromRequestObject("surveyor", request);
        } else {
            surveyor = "";
        }
        
        if (checkParameter("sortBy", request)) {
            sortBy = (String) getParameterFromRequestObject("sortBy", request);
        } else {
            sortBy = "";
        }
        
        if (checkParameter("sortByOrder", request)) {
            sortByOrder = (String) getParameterFromRequestObject("sortByOrder", request);
        } else {
            sortByOrder = "";
        }
        
        if (checkParameter("currentPage", request)) {
            currentPageNo = (String) getParameterFromRequestObject("currentPage", request);
            currentPage = Integer.parseInt(currentPageNo);
        } else {
            currentPageNo = "";
            currentPage = 0;
        }
        
        switch(ptc) {
        	case RESIDENTS:
        		
        		switch (wardNo) {
                case "":
                    break;

                default:
                	caseQuery += " AND a.answer_3 LIKE '" + wardNo + "' ";
                    break;
        		}
        		
        		switch (tole) {
                case "":
                    break;
                case "undefined":
                	break;
                default:
                	caseQuery += " AND a.answer_2 = '" + tole.replace("spacex", " ") + "' ";

                    break;
        		}
        		
        		switch (searchKey) {
                case "":
                    break;

                default:
                	if(searchKey.contains("%")) {
                	try {
                	    String result = java.net.URLDecoder.decode(searchKey, StandardCharsets.UTF_8.name());
                	    searchKey = result;
                	} catch (UnsupportedEncodingException e) {
                	    // not going to happen - value came from JDK's own StandardCharsets
                	}
                	}
                	caseQuery += " AND a.answer_1 LIKE '%" + searchKey + "%'";
                    break;
        		}
        		
        		if(sortBy.equals("")) {

        		switch (action.toLowerCase()) {
                case "next":
                    if (lastSeenId > 0) {
                        caseQuery += " AND a.id < " + lastSeenId;
                        caseQuery += " ORDER BY a.id DESC ";

                    }
                    break;
                case "prev":
                    if (lastSeenId > 0) {
                        caseQuery += " AND a.id > " + lastSeenId;
                        caseQuery += " ORDER BY a.id ASC ";

                    }
                    break;
                default:
                    caseQuery += " ORDER BY a.id DESC ";

                    break;
        		}
        		}else {
        			switch (sortBy.toLowerCase()) {
                    case "ownername":
                            caseQuery += " ORDER BY a.answer_1 " + sortByOrder + " ";
                            if(currentPage >= 0) {
                            caseQuery += " LIMIT " + currentPage * pageSize + ", " + pageSize ;
                            }else {
                            	caseQuery += " LIMIT 0";
                            }
                            	
                        break;
                    case "housenumber":

                        caseQuery += " ORDER BY a.answer_4 " + sortByOrder + " ";
                        if(currentPage >= 0) {
                        caseQuery += " LIMIT " + currentPage * pageSize + ", " + pageSize ;
                        }else {
                        	caseQuery += " LIMIT 0";
                        }
                        break;
                    case "tole":

                        caseQuery += " ORDER BY a.answer_2 " + sortByOrder + " ";
                        if(currentPage >= 0) {
                        caseQuery += " LIMIT " + currentPage * pageSize + ", " + pageSize ;
                        }else {
                        	caseQuery += " LIMIT 0";
                        }
                        break;
                        
                    case "phonenumber":

                        caseQuery += " ORDER BY a.answer_5 " + sortByOrder + " ";
                        if(currentPage >= 0) {
                        caseQuery += " LIMIT " + currentPage * pageSize + ", " + pageSize ;
                        }else {
                        	caseQuery += " LIMIT 0";
                        }
                        break;
                        
                    case "kittanumber":

                        caseQuery += " ORDER BY a.answer_13 " + sortByOrder + " ";
                        if(currentPage >= 0) {
                        caseQuery += " LIMIT " + currentPage * pageSize + ", " + pageSize ;
                        }else {
                        	caseQuery += " LIMIT 0";
                        }
                        break;
                    
                    case "familysize":
                    	caseQuery += " ORDER BY totalFamilyMembers " + sortByOrder + " ";
                        if(currentPage >= 0) {
                        caseQuery += " LIMIT " + currentPage * pageSize + ", " + pageSize ;
                        }else {
                        	caseQuery += " LIMIT 0";
                        }
                        break;
                    default:
                        caseQuery += " ORDER BY a.id DESC ";
                        break;
            		}
        		}
        		
        		
        		break;
        		
        		
        	case FAMILY_MEMBERS:
        		
        		switch (wardNo) {
                case "":
                    break;

                default:
                	caseQuery += " AND a.answer_3 LIKE '" + wardNo + "' ";
                    break;
        		}
        		
        		
        		switch (searchKey) {
                case "":
                    break;

                default:
                	if(searchKey.contains("%")) {
                	try {
                	    String result = java.net.URLDecoder.decode(searchKey, StandardCharsets.UTF_8.name());
                	    searchKey = result;
                	} catch (UnsupportedEncodingException e) {
                	    // not going to happen - value came from JDK's own StandardCharsets
                	}
                	}
                	caseQuery += " AND fm.full_name LIKE '%" + searchKey + "%'";
                    break;
        		}
        		
        		if(sortBy.equals("")) {
        		switch (action.toLowerCase()) {
                case "next":
                    if (lastSeenId > 0) {
                        caseQuery += " AND fm.id < " + lastSeenId;
                        caseQuery += " ORDER BY fm.id DESC ";
                    }
                    break;
                case "prev":
                    if (lastSeenId > 0) {
                        caseQuery += " AND fm.id > " + lastSeenId;
                        caseQuery += " ORDER BY fm.id ASC ";
                    }
                    break;
                default:
                    caseQuery += " ORDER BY fm.id DESC ";
                    break;
        		}
        		}else {
        			switch (sortBy.toLowerCase()) {
                    case "fullname":
                            caseQuery += " ORDER BY fm.full_name " + sortByOrder + " ";
                            if(currentPage >= 0) {
                            caseQuery += " LIMIT " + currentPage * pageSize + ", " + pageSize ;
                            }else {
                            	caseQuery += " LIMIT 0";
                            }
                            	
                        break;
                    case "age":
                    	caseQuery += " ORDER BY fm.age " + sortByOrder + " ";
                        if(currentPage >= 0) {
                        caseQuery += " LIMIT " + currentPage * pageSize + ", " + pageSize ;
                        }else {
                        	caseQuery += " LIMIT 0";
                        }
                        break;
                    case "gender":
                    	caseQuery += " ORDER BY fm.gender_id " + sortByOrder + " ";
                        if(currentPage >= 0) {
                        caseQuery += " LIMIT " + currentPage * pageSize + ", " + pageSize ;
                        }else {
                        	caseQuery += " LIMIT 0";
                        }
                        break;
                        
                    case "education":
                    	caseQuery += " ORDER BY fm.qualification_id " + sortByOrder + " ";
                        if(currentPage >= 0) {
                        caseQuery += " LIMIT " + currentPage * pageSize + ", " + pageSize ;
                        }else {
                        	caseQuery += " LIMIT 0";
                        }
                        break;
                        
                    case "maritalstatus":
                    	caseQuery += " ORDER BY fm.marital_status " + sortByOrder + " ";
                        if(currentPage >= 0) {
                        caseQuery += " LIMIT " + currentPage * pageSize + ", " + pageSize ;
                        }else {
                        	caseQuery += " LIMIT 0";
                        }
                        break;

                    default:
                        caseQuery += " ORDER BY fm.id DESC ";
                        break;
            		}
        		}
        		
        		break;
        		
        	case FAV_PLACES:
        		
        		switch (wardNo) {
                case "":
                    break;
                    
          		case "undefined":
        			break;

                default:
                	caseQuery += " AND fp.fav_place_ward LIKE '" + wardNo + "' ";
                    break;
        		}
        		
        		switch (placeType) {
                case "":
                    break;
                    
          		case "undefined":
        			break;

                default:
                	caseQuery += " AND fp.fav_place_type LIKE '" + placeType + "' ";
                    break;
        		}
        		
        		
        		switch (surveyor) {
        		case "":
                    break;
                
        		case "undefined":
        			break;

                default:
                	caseQuery += " AND fp.added_by LIKE '" + surveyor + "' ";
                    break;
        		}
        		
        		
        		switch (searchKey) {
                case "":
                    break;

                default:
                	if(searchKey.contains("%")) {
                	try {
                	    String result = java.net.URLDecoder.decode(searchKey, StandardCharsets.UTF_8.name());
                	    searchKey = result;
                	} catch (UnsupportedEncodingException e) {
                	    // not going to happen - value came from JDK's own StandardCharsets
                	}
                	}
                	caseQuery += " AND fp.fav_place_name LIKE '%" + searchKey + "%'";
                    break;
        		}
        		
        		if(sortBy.equals("")) {
        		switch (action.toLowerCase()) {
                case "next":
                    if (lastSeenId > 0) {
                        caseQuery += " AND fp.id < " + lastSeenId;
                        caseQuery += " ORDER BY fp.id DESC ";
                    }
                    break;
                case "prev":
                    if (lastSeenId > 0) {
                        caseQuery += " AND fp.id > " + lastSeenId;
                        caseQuery += " ORDER BY fp.id ASC ";
                    }
                    break;
                default:
                    caseQuery += " ORDER BY fp.id DESC ";
                    break;
        		}
        		
        		} else {
        			switch (sortBy.toLowerCase()) {
                    case "placename":
                            caseQuery += " ORDER BY fp.fav_place_name " + sortByOrder + " ";
                            if(currentPage >= 0) {
                            caseQuery += " LIMIT " + currentPage * pageSize + ", " + pageSize ;
                            }else {
                            	caseQuery += " LIMIT 0";
                            }
                        break;
                    case "placeward":
                    	caseQuery += " ORDER BY fp.fav_place_ward " + sortByOrder + " ";
                        if(currentPage >= 0) {
                        caseQuery += " LIMIT " + currentPage * pageSize + ", " + pageSize ;
                        }else {
                        	caseQuery += " LIMIT 0";
                        }
                        break;
                        
                    default:
                        caseQuery += " ORDER BY fp.id DESC ";
                        break;
            		}
        		}
        		break;
        		
        	default: 
        		break;
        }
        if(sortBy.equals("")) {
        caseQuery += "LIMIT " + pageSize;
        }
        return caseQuery;
    }
    
    // This function checks whether there is given parameter in request object
    public static boolean checkParameter(String parameterName, HttpServletRequest request) {
        return (request.getParameter(parameterName) != null ? true : false);
    }

    // This function returns request parameter values as generic type. You have to
    // cast the result in caller object
    @SuppressWarnings("unchecked")
    public static <T> T getParameterFromRequestObject(String parameter, HttpServletRequest request) {
        return (T) request.getParameter(parameter);
    }
}
