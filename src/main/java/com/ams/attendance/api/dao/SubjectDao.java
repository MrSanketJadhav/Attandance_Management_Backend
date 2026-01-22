package com.ams.attendance.api.dao;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ams.attendance.api.entity.Subject;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;



@Repository
public class SubjectDao {

	@Autowired
	private SessionFactory factory;
	
	public Subject getSubjectById(long subjectId) {
		Session session = null;
		Subject subject = null;
		try {
			session = factory.openSession();
			subject = session.get(Subject.class, subjectId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return subject;
	}
	
	public List<Subject> getAllSubjects() {
	    Session session = null;
	    List<Subject> list = null;
	    try {
	        session = factory.openSession();

	        CriteriaBuilder cb = session.getCriteriaBuilder();
	        CriteriaQuery<Subject> cq = cb.createQuery(Subject.class);
	        Root<Subject> root = cq.from(Subject.class);
	        cq.select(root);

	        list = session.createQuery(cq).getResultList();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (session != null) session.close();
	    }
	    return list;
	}
	
	public Subject createSubject(Subject subject) {
	    Session session = null;
	    Subject sub = null;

	    try {
	        session = factory.openSession();
	        Transaction transaction = session.beginTransaction();

	        // Build criteria: SELECT s FROM Subject s WHERE s.name = :name
	        CriteriaBuilder cb = session.getCriteriaBuilder();
	        CriteriaQuery<Subject> cq = cb.createQuery(Subject.class);
	        Root<Subject> root = cq.from(Subject.class);
	        cq.select(root).where(cb.equal(root.get("name"), subject.getName()));

	        List<Subject> list = session.createQuery(cq).getResultList();

	        if (list.isEmpty()) {
	            session.save(subject);
	            transaction.commit();
	            sub = subject;
	        } else {
	            transaction.rollback(); // Optional: rollback if already exists
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (session != null) session.close();
	    }
	    return sub;
	}
	
	public Subject updateSubject(Subject subjectDetails) {
		Session session = null;
		Subject sub = null;
		try {
			session = factory.openSession();
			Transaction transaction = session.beginTransaction();
			session.update(subjectDetails);
			transaction.commit();
			sub = subjectDetails;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return sub;
	}

	public String deleteSubject(long id) {
		Session session = null;
		String msg = null;
		try {
			session = factory.openSession();
			Subject subject = session.get(Subject.class, id);
			session.delete(subject);
			session.beginTransaction().commit();
			msg = "deleted";

		} catch (Exception e) {
			msg = null;
			e.printStackTrace();
		} finally {
			session.close();
		}
		return msg;
	}
}
