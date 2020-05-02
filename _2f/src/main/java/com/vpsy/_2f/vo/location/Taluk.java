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
 * @description The class represents <b>Taluk</b> table.
 */
@Entity(name = "Taluk")
@Table(name = "taluk_2f")
public class Taluk {
	
	@Id
	@Column(name = "taluk_id", unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "taluk_name")
	@NotNull(message = "Please provide taluk name")
	private String name;

	@Column(name = "taluk_name_kannada")
	@NotNull(message = "Please provide taluk name in Kannada")
	private String nameKannada;

	@Column(name = "taluk_name_hindi")
	@NotNull(message = "Please provide taluk name in Hindi")
	private String nameHindi;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "district_id_fk")
	private District district;
	
	public Taluk() {
		super();
	}
	
	public Taluk(Taluk taluk) {
		this.id = taluk.id;
		this.name = taluk.name;
		this.nameKannada = taluk.nameKannada;
		this.nameHindi = taluk.nameHindi;
		this.district = new District(taluk.district);
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

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	@Override
	public String toString() {
		return "Taluk [id=" + id + ", name=" + name + ", nameKannada=" + nameKannada + ", nameHindi=" + nameHindi
				+ ", district=" + district + "]";
	}
}
