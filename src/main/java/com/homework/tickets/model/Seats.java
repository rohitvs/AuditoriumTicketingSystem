package com.homework.tickets.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="seats")
public class Seats {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="seat_id")
	private Integer seatId;
	@Column(name="row_id")
	private Integer rowId;
	@Column(name="level_id")
	private Integer levelId;
	@Column(name="seat_num")
	private Integer seatNum;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "seats" ,targetEntity = HoldingSeatMap.class)
	private List<HoldingSeatMap> holdingSeatMap;
	
	@OneToOne(fetch=FetchType.LAZY, mappedBy = "seats",targetEntity = BookingSeatMap.class)
	private BookingSeatMap bookingSeatMap;
	
	/*@ManyToOne(targetEntity = AuditoriumMetaData.class)
	private AuditoriumMetaData auditoriumMetaData;*/
	
	
	public Integer getSeatId() {
		return seatId;
	}
	public void setSeatId(Integer seatId) {
		this.seatId = seatId;
	}
	public Integer getRowId() {
		return rowId;
	}
	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}
	public Integer getLevelId() {
		return levelId;
	}
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}
	public Integer getSeatNum() {
		return seatNum;
	}
	public void setSeatNum(Integer seatNum) {
		this.seatNum = seatNum;
	}
	public Seats(Integer seatId, Integer rowId, Integer levelId, Integer seatNum) {
		super();
		this.seatId = seatId;
		this.rowId = rowId;
		this.levelId = levelId;
		this.seatNum = seatNum;
	}
	public Seats() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Seats [seatId=" + seatId + ", rowId=" + rowId + ", levelId=" + levelId + ", seatNum=" + seatNum
				+ ", holdingSeatMap=" + holdingSeatMap + ", bookingSeatMap=" + bookingSeatMap + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
//		result = prime * result + ((auditoriumMetaData == null) ? 0 : auditoriumMetaData.hashCode());
		result = prime * result + ((bookingSeatMap == null) ? 0 : bookingSeatMap.hashCode());
		result = prime * result + ((holdingSeatMap == null) ? 0 : holdingSeatMap.hashCode());
		result = prime * result + ((levelId == null) ? 0 : levelId.hashCode());
		result = prime * result + ((rowId == null) ? 0 : rowId.hashCode());
		result = prime * result + ((seatId == null) ? 0 : seatId.hashCode());
		result = prime * result + ((seatNum == null) ? 0 : seatNum.hashCode());
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
		Seats other = (Seats) obj;
//		if (auditoriumMetaData == null) {
//			if (other.auditoriumMetaData != null)
//				return false;
//		} else if (!auditoriumMetaData.equals(other.auditoriumMetaData))
//			return false;
		if (bookingSeatMap == null) {
			if (other.bookingSeatMap != null)
				return false;
		} else if (!bookingSeatMap.equals(other.bookingSeatMap))
			return false;
		if (holdingSeatMap == null) {
			if (other.holdingSeatMap != null)
				return false;
		} else if (!holdingSeatMap.equals(other.holdingSeatMap))
			return false;
		if (levelId == null) {
			if (other.levelId != null)
				return false;
		} else if (!levelId.equals(other.levelId))
			return false;
		if (rowId == null) {
			if (other.rowId != null)
				return false;
		} else if (!rowId.equals(other.rowId))
			return false;
		if (seatId == null) {
			if (other.seatId != null)
				return false;
		} else if (!seatId.equals(other.seatId))
			return false;
		if (seatNum == null) {
			if (other.seatNum != null)
				return false;
		} else if (!seatNum.equals(other.seatNum))
			return false;
		return true;
	}
	
	public BookingSeatMap getBookingSeatMap() {
		return bookingSeatMap;
	}
	public void setBookingSeatMap(BookingSeatMap bookingSeatMap) {
		this.bookingSeatMap = bookingSeatMap;
	}
	/*public AuditoriumMetaData getAuditoriumMetaData() {
		return auditoriumMetaData;
	}
	public void setAuditoriumMetaData(AuditoriumMetaData auditoriumMetaData) {
		this.auditoriumMetaData = auditoriumMetaData;
	}*/
	public List<HoldingSeatMap> getHoldingSeatMap() {
		return holdingSeatMap;
	}
	public void setHoldingSeatMap(List<HoldingSeatMap> holdingSeatMap) {
		this.holdingSeatMap = holdingSeatMap;
	}
	
	
}
