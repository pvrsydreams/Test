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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author punith
 * @date 23-Apr-2020
 * @description The class represents <b>District</b> table.
 */
@Entity(name = "District")
@Table(name = "district_2f")
public class District {

	@Id
	@Column(name = "district_id", unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "district_name")
	@NotNull(message = "Please provide district name")
	private String name;

	@Column(name = "district_name_kannada")
	@NotNull(message = "Please provide district name in Kannada")
	private String nameKannada;

	@Column(name = "district_name_hindi")
	@NotNull(message = "Please provide district name in Hindi")
	private String nameHindi;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "state_id_fk")
	private State state;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "district_id_fk")
	private Set<Taluk> taluks;
	
	public District() {
		super();
	}
	
	public District(District district) {
		this.id = district.id;
		this.name = district.name;
		this.nameKannada = district.nameKannada;
		this.nameHindi = district.nameHindi;
		this.state = new State(district.state);
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Set<Taluk> getTaluks() {
		return taluks;
	}
	
	public void setTaluks(Set<Taluk> taluks) {
		this.taluks = taluks;
	}

	@Override
	public String toString() {
		return "District [id=" + id + ", name=" + name + ", nameKannada=" + nameKannada + ", nameHindi=" + nameHindi
				+ ", state=" + state + "]";
	}
}
