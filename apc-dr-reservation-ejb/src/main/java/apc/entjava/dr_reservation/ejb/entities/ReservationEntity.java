package apc.entjava.dr_reservation.ejb.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservations")
public class ReservationEntity implements Comparable<ReservationEntity> {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "student_name")
    private String studentName;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;

    @Column(name = "reservation_date")
    private Date date;

    @Column(name = "start_time")
    private int startTime;

    @Column(name = "end_time")
    private int endTime;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "endorser")
    private String endorser;


    public ReservationEntity() {

    }

    public ReservationEntity(int id, String studentId, String studentName, RoomEntity room, Date date, int startTime, int endTime, String purpose, String endorser) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.room = room;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.purpose = purpose;
        this.endorser = endorser;
    }

    @Override
    public int compareTo(ReservationEntity other) {
        return getStartTime() - other.getStartTime();
    }

    public boolean conflictsWith(ReservationEntity other) {
        if (getEndTime() <= other.getStartTime()) {
            return false;
        }
        if (other.getEndTime() <= getStartTime()) {
            return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getEndorser() {
        return endorser;
    }

    public void setEndorser(String endorser) {
        this.endorser = endorser;
    }
}
