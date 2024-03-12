package main.java.com.ubo.tp.message.core.database;

import main.java.com.ubo.tp.message.datamodel.Message;
import main.java.com.ubo.tp.message.datamodel.User;

public class DatabaseImpl implements IDatabaseObserver {
	@Override
	public void notifyMessageAdded(Message addedMessage) {
		System.out.println("Message added: " + addedMessage.getText());
	}

	@Override
	public void notifyMessageDeleted(Message deletedMessage) {
		System.out.println("Message deleted: " + deletedMessage.getText());
	}

	@Override
	public void notifyMessageModified(Message modifiedMessage) {
		System.out.println("Message modified: " + modifiedMessage.getText());
	}

	@Override
	public void notifyUserAdded(User addedUser) {
		System.out.println("User added: " + addedUser.getName());
	}

	@Override
	public void notifyUserDeleted(User deletedUser) {
		System.out.println("User deleted: " + deletedUser.getName());
	}

	@Override
	public void notifyUserModified(User modifiedUser) {
		System.out.println("User modified: " + modifiedUser.getName());
	}
}
