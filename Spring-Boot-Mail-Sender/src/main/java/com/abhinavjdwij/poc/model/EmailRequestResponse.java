package com.abhinavjdwij.poc.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class EmailRequestResponse implements Serializable {
	private static final long serialVersionUID = -4118923929248435529L;
	private String status;
	private String message;
	private List<String> toList;
	private List<String> ccList;
	private List<String> bccList;
	private List<String> attachmentPathList;
	private String subject;
	private String body;
}
