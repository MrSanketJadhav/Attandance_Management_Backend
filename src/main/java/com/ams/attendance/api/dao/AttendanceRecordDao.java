package com.ams.attendance.api.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ams.attendance.api.entity.AttendanceRecord;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;


@Repository
public class AttendanceRecordDao {

	@Autowired
	private SessionFactory factory;
	
	public List<AttendanceRecord> getAllAttendanceRecords() {
	    Session session = null;
	    List<AttendanceRecord> list = null;
	    try {
	        session = factory.openSession();

	        CriteriaBuilder cb = session.getCriteriaBuilder();
	        CriteriaQuery<AttendanceRecord> cq = cb.createQuery(AttendanceRecord.class);
	        Root<AttendanceRecord> root = cq.from(AttendanceRecord.class);
	        cq.select(root).distinct(true); // Equivalent to DISTINCT_ROOT_ENTITY

	        list = session.createQuery(cq).getResultList();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (session != null) {
	            session.close();
	        }
	    }
	    return list;
	}
	
	public List<AttendanceRecord> getAttendanceByFacultySubjectDate(String faculty, long subjectId, String date) {
	    Session session = null;
	    List<AttendanceRecord> list = null;
	    try {
	        session = factory.openSession();
	        CriteriaBuilder cb = session.getCriteriaBuilder();
	        CriteriaQuery<AttendanceRecord> cq = cb.createQuery(AttendanceRecord.class);
	        Root<AttendanceRecord> root = cq.from(AttendanceRecord.class);

	        // Join with related entities
	        Join<Object, Object> userJoin = root.join("user");     // assuming field name is 'user'
	        Join<Object, Object> subjectJoin = root.join("subject"); // assuming field name is 'subject'

	        // WHERE u.username = :faculty AND s.id = :subjectId AND date = :date
	        cq.select(root).where(
	            cb.and(
	                cb.equal(userJoin.get("username"), faculty),
	                cb.equal(subjectJoin.get("id"), subjectId),
	                cb.equal(root.get("date"), date)
	            )
	        ).distinct(true);  // to avoid duplicates

	        list = session.createQuery(cq).getResultList();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (session != null) session.close();
	    }
	    return list;
	}
	
	public List<AttendanceRecord> getAttendanceByFaculty(String facultyUsername) {
	    Session session = null;
	    List<AttendanceRecord> list = null;
	    try {
	        session = factory.openSession();
	        CriteriaBuilder cb = session.getCriteriaBuilder();
	        CriteriaQuery<AttendanceRecord> cq = cb.createQuery(AttendanceRecord.class);
	        Root<AttendanceRecord> root = cq.from(AttendanceRecord.class);

	        // Join with 'user' field (assumed to be a User entity)
	        Join<Object, Object> userJoin = root.join("user");

	        // WHERE user.username = :facultyUsername
	        cq.select(root).where(cb.equal(userJoin.get("username"), facultyUsername));

	        list = session.createQuery(cq).getResultList();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (session != null) session.close();
	    }
	    return list;
	}
	
	public List<AttendanceRecord> getAllAttendanceRecords(String date, long subjectId) {
	    Session session = null;
	    List<AttendanceRecord> list = null;
	    try {
	        session = factory.openSession();

	        CriteriaBuilder cb = session.getCriteriaBuilder();
	        CriteriaQuery<AttendanceRecord> cq = cb.createQuery(AttendanceRecord.class);
	        Root<AttendanceRecord> root = cq.from(AttendanceRecord.class);

	        // Join with the subject entity
	        Join<Object, Object> subjectJoin = root.join("subject");

	        cq.select(root).where(
	            cb.and(
	                cb.equal(root.get("date"), date),
	                cb.equal(subjectJoin.get("id"), subjectId)
	            )
	        );

	        list = session.createQuery(cq).getResultList();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (session != null) session.close();
	    }
	    return list;
	}
	
	public AttendanceRecord saveAttendance(AttendanceRecord attendanceRecord) {
		Session session = null;
		AttendanceRecord record = null;
		try {
			session = factory.openSession();
			Transaction transaction = session.beginTransaction();
			session.save(attendanceRecord);
			transaction.commit();
			record = attendanceRecord;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return record;
	}
}
