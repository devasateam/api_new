package com.onliofli.utils;

import java.util.ArrayList;
import java.util.List;

public class MessageList {
	List<Message> errorMessage = null;
	List<Message> successMessage = null;

	public MessageList() {
		errorMessage = new ArrayList<>();
		successMessage = new ArrayList<>();
	}

	public void addErrorMessage(Message message) {
		errorMessage.add(message);
	}

	public void addSuccessMessage(Message message) {
		successMessage.add(message);
	}

	public List<Message> getErrorMessage() {
		return errorMessage;
	}

	public List<Message> getSuccessMessage() {
		return successMessage;
	}

	public boolean hasErrors() {
		return (!errorMessage.isEmpty());
	}

}
