/**
 * @author Umesh Bhujel <yoomesbhujel@gmail.com>
 * Since Dec 20, 2019
 */
package com.ishanitech.ipalika.service;

import java.util.List;

import com.ishanitech.ipalika.model.FormDetail;

public interface FormDetailService {

	List<FormDetail> getFormDetailById(Integer formId);

}
