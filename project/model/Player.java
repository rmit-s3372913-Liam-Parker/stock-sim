package model;

import javafx.beans.property.*;


public class Player {

	private IntegerProperty id;
	private StringProperty userName;
	private StringProperty email;
	private StringProperty confirmed;
	private DoubleProperty winning;
	
	public Player() {
		// TODO Auto-generated constructor stub
	}
	
	public Player(int id, String userName, String email, String confirmed, double winning) {
		this.id = new SimpleIntegerProperty(id);
		this.userName = new SimpleStringProperty(userName);
		this.email = new SimpleStringProperty(email);
		this.confirmed = new SimpleStringProperty(confirmed);
		this.winning = new SimpleDoubleProperty(winning);
	}

	
	
	public int getId() {
		return id.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public String getUserName() {
		return userName.get();
	}

	public void setUserName(String userName) {
		this.userName.set(userName);
	}

	public String getEmail() {
		return email.get();
	}

	public void setEmail(String email) {
		this.email.set(email);
	}

	public String getConfirmed() {
		return confirmed.get();
	}

	public void setConfirmed(String confirmed) {
		this.confirmed.set(confirmed);
	}

	public Double getWinning() {
		return winning.get();
	}

	public void setWinning(double winning) {
		this.winning.set(winning);
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", userName=" + userName + ", email=" + email + ", confirmed=" + confirmed
				+ ", winning=" + winning + "]";
	}
	
	

}
