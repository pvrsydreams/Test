package com.vpsy._2f.vo.location;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author punith
 * @date 22-Apr-2020
 * @description The class represents <b>State</b> table.
 */
@Entity(name = "State")
@Table(name = "state_2f")
public class State {

	@Id
	@Column(name = "state_id", unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "state_name")
	@NotNull(message = "Please provide state name")
	private String name;

	@Column(name = "state_name_kannada")
	@NotNull(message = "Please provide state name in Kannada")
	private String nameKannada;

	@Column(name = "state_name_hindi")
	@NotNull(message = "Please provide state name in Hindi")
	private String nameHindi;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "state_id_fk")
	private Set<District> districts;

	public State() {
		super();
	}
	
	public State(State state) {
		this.id = state.id;
		this.name = state.name;
		this.nameKannada = state.nameKannada;
		this.nameHindi = state.nameHindi;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameKannada() {
		return nameKannada;
	}

	public void setNameKannada(String nameKannada) {
		this.nameKannada = nameKannada;
	}

	public String getNameHindi() {
		return nameHindi;
	}

	public void setNameHindi(String nameHindi) {
		this.nameHindi = nameHindi;
	}

	public Set<District> getDistricts() {
		return districts;
	}

	public void setDistricts(Set<District> districts) {
		this.districts = districts;
	}

	@Override
	public String toString() {
		return "State [id=" + id + ", name=" + name + ", nameKannada=" + nameKannada + ", nameHindi=" + nameHindi + "]";
	}
}
