package com.vpsy._2f.vo.product;

import com.vpsy._2f.vo.advertise.Ad;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author punith
 * @date 24-Apr-2020
 * @description The class represents <b>Item</b> table.
 */
@Entity(name = "Item")
@Table(name = "item_2f")
public class Item {
	
	@Id
	@Column(name = "item_id", unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "item_name")
	@NotNull(message = "Please provide item name")
	private String name;

	@Column(name = "item_name_kannada")
	@NotNull(message = "Please provide item name in Kannada")
	private String nameKannada;

	@Column(name = "item_name_hindi")
	@NotNull(message = "Please provide item name in Hindi")
	private String nameHindi;
	
	@Column(name = "item_unit")
	@NotNull(message = "Please provide item unit")
	private String unit;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "item_icon")
	private String icon;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "item_type_id_fk")
	private ItemType itemType;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "item_id_fk")
	private Set<Ad> ads;

	public Item() {
		super();
	}
	
	public Item(Item item) {
		this.id = item.id;
		this.name = item.name;
		this.nameKannada = item.nameKannada;
		this.nameHindi = item.nameHindi;
		this.unit = item.unit;
		this.description = item.description;
		this.itemType = new ItemType(item.itemType);
	}

	public Integer getId() {
		return id;
	}

	public Item setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Item setName(String name) {
		this.name = name;
		return this;
	}

	public String getNameKannada() {
		return nameKannada;
	}

	public Item setNameKannada(String nameKannada) {
		this.nameKannada = nameKannada;
		return this;
	}

	public String getNameHindi() {
		return nameHindi;
	}

	public Item setNameHindi(String nameHindi) {
		this.nameHindi = nameHindi;
		return this;
	}

	public String getUnit() {
		return unit;
	}

	public Item setUnit(String unit) {
		this.unit = unit;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Item setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getIcon() {
		return icon;
	}

	public Item setIcon(String icon) {
		this.icon = icon;
		return this;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public Item setItemType(ItemType itemType) {
		this.itemType = itemType;
		return this;
	}

	public Set<Ad> getAds() {
		return ads;
	}

	public Item setAds(Set<Ad> ads) {
		this.ads = ads;
		return this;
	}

	@Override
	public String toString() {
		return "Item{" +
				"id=" + id +
				", name='" + name + '\'' +
				", nameKannada='" + nameKannada + '\'' +
				", nameHindi='" + nameHindi + '\'' +
				", unit='" + unit + '\'' +
				", description='" + description + '\'' +
				", icon='" + icon + '\'' +
				", itemType=" + itemType +
				'}';
	}
}
