package com.vpsy._2f.vo.product;

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
 * @date 24-Apr-2020
 * @description The class represents <b>ItemType</b> table.
 */
@Entity(name = "ItemType")
@Table(name = "item_type_2f")
public class ItemType {
	
	@Id
	@Column(name = "item_type_id", unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "item_type_name")
	@NotNull(message = "Please provide state name")
	private String name;

	@Column(name = "item_type_name_kannada")
	@NotNull(message = "Please provide state name in Kannada")
	private String nameKannada;

	@Column(name = "item_type_name_hindi")
	@NotNull(message = "Please provide state name in Hindi")
	private String nameHindi;
	
	@Column(name = "description")
	private String description;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "item_type_id_fk")
	private Set<Item> items;
	
	public ItemType() {
		super();
	}
	
	public ItemType(ItemType itemType) {
		this.id = itemType.id;
		this.name = itemType.name;
		this.nameKannada = itemType.nameKannada;
		this.nameHindi = itemType.nameHindi;
		this.description = itemType.description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "ItemType [id=" + id + ", name=" + name + ", nameKannada=" + nameKannada + ", nameHindi=" + nameHindi
				+ ", description=" + description + "]";
	}
}
