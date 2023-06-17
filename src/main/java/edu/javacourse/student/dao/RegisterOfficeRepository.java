package edu.javacourse.student.dao;

import edu.javacourse.student.domain.RegisterOffice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterOfficeRepository extends JpaRepository<RegisterOffice, Long> {
}
