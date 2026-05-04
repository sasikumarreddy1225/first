package com.srp.core.services;

import java.io.IOException;

import com.srp.core.beans.request.PublishContentRequestBean;
import com.srp.core.beans.response.LabelsResponseBean;

public interface ApiServiceCall {
	
	public LabelsResponseBean getSRPLabels(String req) throws IOException;
	
	public String updateSRPContent(PublishContentRequestBean publishContentRequestBean) throws IOException;

	
}
