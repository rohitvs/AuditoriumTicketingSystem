package com.homework.tickets.model;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="AUDITORIUM_METADATA")
public class AuditoriumMetaData {

	@Id
    @GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="level_id")
	private Integer levelId;
	@Column(name="level_name")
	private String levelName;
	@Column(name="price")
	private Double price;
	@Column(name="total_rows")
	private Integer totalRows;
	@Column(name="seats_in_row")
	private Integer seatsInRow;
	
//	@OneToMany()
//	@JoinColumn(name = "level_id")
//	private List<Seats> seats;
	
	public Integer getLevelId() {
		return levelId;
	}
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getRoralRows() {
		return totalRows;
	}
	public void setRoralRows(Integer roralRows) {
		this.totalRows = roralRows;
	}
	public Integer getSeatsInRow() {
		return seatsInRow;
	}
	public void setSeatsInRow(Integer seatsInRow) {
		this.seatsInRow = seatsInRow;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((levelId == null) ? 0 : levelId.hashCode());
		result = prime * result + ((levelName == null) ? 0 : levelName.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
//		result = prime * result + ((seats == null) ? 0 : seats.hashCode());
		result = prime * result + ((seatsInRow == null) ? 0 : seatsInRow.hashCode());
		result = prime * result + ((totalRows == null) ? 0 : totalRows.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuditoriumMetaData other = (AuditoriumMetaData) obj;
		if (levelId == null) {
			if (other.levelId != null)
				return false;
		} else if (!levelId.equals(other.levelId))
			return false;
		if (levelName == null) {
			if (other.levelName != null)
				return false;
		} else if (!levelName.equals(other.levelName))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
//		if (seats == null) {
//			if (other.seats != null)
//				return false;
//		} else if (!seats.equals(other.seats))
//			return false;
		if (seatsInRow == null) {
			if (other.seatsInRow != null)
				return false;
		} else if (!seatsInRow.equals(other.seatsInRow))
			return false;
		if (totalRows == null) {
			if (other.totalRows != null)
				return false;
		} else if (!totalRows.equals(other.totalRows))
			return false;
		return true;
	}
	public AuditoriumMetaData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AuditoriumMetaData(Integer levelId, String levelName, Double price, Integer roralRows,
			Integer seatsInRow) {
		super();
		this.levelId = levelId;
		this.levelName = levelName;
		this.price = price;
		this.totalRows = roralRows;
		this.seatsInRow = seatsInRow;
	}
	@Override
	public String toString() {
		return "AuditoriumMetaData [levelId=" + levelId + ", levelName=" + levelName + ", price=" + price
				+ ", totalRows=" + totalRows + ", seatsInRow=" + seatsInRow +"]";
	}
	/*public List<Seats> getSeats() {
		return seats;
	}
	public void setSeats(List<Seats> seats) {
		this.seats = seats;
	}*/
	
	
}
