package edu.javacourse.student.business;

import edu.javacourse.student.dao.*;
import edu.javacourse.student.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentOrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentOrderService.class);
    @Autowired
    private PassportOfficeRepository daoPassportOffice;
    @Autowired
    private RegisterOfficeRepository daoRegister;
    @Autowired
    private StreetRepository daoStreet;
    @Autowired
    private StudentOrderRepository dao;
    @Autowired
    private StudentOrderStatusRepository daoSOStatus;
    @Autowired
    private UniversityRepository daoUniversity;
    @Autowired
    private StudentOrderChildRepository daoChild;


    @Transactional
    public void testSave() {
        StudentOrder so = new StudentOrder();
        so.setStudentOrderDate(LocalDateTime.now());
        so.setStatus(daoSOStatus.findById(1L).get());
        so.setHusband(buildPerson(false));
        so.setWife(buildPerson(true));
        so.setCertificateNumber("Certificate");
        so.setRegisterOffice(daoRegister.findById(1L).get());
        so.setMarriageDate(LocalDate.now());

        dao.save(so);
        StudentOrderChild soc = buildChild(so);
        daoChild.save(soc);
    }

    @Transactional
    public void testGet() {
        List<StudentOrder> sos = dao.findAll();
        LOGGER.info(sos.get(0).getWife().getSurName());
        LOGGER.info(sos.get(0).getChildren().get(0).getChild().getGivenName());
    }

    private Adult buildPerson(boolean wife) {
        Adult p = new Adult();
        p.setDateOfBirth(LocalDate.now());
        Address adr = new Address();
        adr.setPostCode("1900000");
        adr.setBuilding("21");
        adr.setExtension("Б");
        adr.setApartment("199");
        adr.setStreet(daoStreet.findById(1L).get());
        p.setAddress(adr);
        if (wife) {
            p.setSurName("Петрова");
            p.setGivenName("Марфа");
            p.setPatronymic("Васильевна");
            p.setPassportSeria("WIFE_S");
            p.setPassportNumber("WIFE_N");
            p.setPassportOffice(daoPassportOffice.findById(1L).get());
            p.setIssueDate(LocalDate.now());
            p.setStudentNumber("12345");
            p.setUniversity(daoUniversity.findById(1L).get());
        } else {
            p.setSurName("Рюрик");
            p.setGivenName("Иван");
            p.setPatronymic("Васильевич");
            p.setPassportSeria("HUSB_S");
            p.setPassportNumber("HUSB_N");
            p.setIssueDate(LocalDate.now());
            p.setPassportOffice(daoPassportOffice.findById(1L).get());
            p.setStudentNumber("67890");
            p.setUniversity(daoUniversity.findById(1L).get());
        }
        return p;
    }

    private StudentOrderChild buildChild(StudentOrder so) {
        Child child = new Child();
        StudentOrderChild soc = new StudentOrderChild();
        Address adr = new Address();
        adr.setPostCode("1900000");
        adr.setBuilding("21");
        adr.setExtension("Б");
        adr.setApartment("199");
        adr.setStreet(daoStreet.findById(1L).get());
        child.setAddress(adr);
        child.setSurName("Рюрик");
        child.setGivenName("Дмитрий");
        child.setPatronymic("Иванович");
        child.setCertificateNumber("12345");
        child.setCertificateDate(LocalDate.now());
        child.setRegisterOffice(daoRegister.findById(1L).get());
        child.setDateOfBirth(LocalDate.now());
        soc.setChild(child);
        soc.setStudentOrder(so);
        soc.setStudentOrderChildId(1L);
        return soc;
    }
}
