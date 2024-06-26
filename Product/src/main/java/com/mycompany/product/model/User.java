package com.mycompany.product.model;



import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sun.istack.Nullable;

@Entity
@Table(name="users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long idUser;
    @Column(name="user_name")
    private String userName;
    @Nullable
    private String adresse;
	private String email;
    @Nullable
    private String tel;
    private String password;
    private String role;

    @Nullable
    @OneToMany(mappedBy="client")
    private Collection<Commande> commandes;
    public User(Long idUser, String userName, String adresse, String email, String tel, String password, String role,
			Collection<Commande> commandes) {
		super();
		this.idUser = idUser;
		this.userName = userName;
		this.adresse = adresse;
		this.email = email;
		this.tel = tel;
		this.password = password;
		this.role = role;
		this.commandes = commandes;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Collection<Commande> getCommandes() {
		return commandes;
	}

	public void setCommandes(Collection<Commande> commandes) {
		this.commandes = commandes;
	}





}