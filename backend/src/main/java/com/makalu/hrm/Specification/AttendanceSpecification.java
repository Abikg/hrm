package com.makalu.hrm.Specification;

import com.makalu.hrm.domain.PersistentAttendanceEntity;
import com.makalu.hrm.model.AttendanceDto;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class AttendanceSpecification implements Specification<PersistentAttendanceEntity> {
    private  final AttendanceDto attendanceDto;

    @Override
    public Predicate toPredicate(Root<PersistentAttendanceEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
     final List<Predicate> predicateList=new ArrayList<>();
      if(attendanceDto.getId()!=null){
        predicateList.add(criteriaBuilder.equal(root.get("user").get("id"),attendanceDto.getId()));
      }
      if(attendanceDto.getFromDate()!=null){
          predicateList.add(criteriaBuilder.between(root.get("createdDate"),attendanceDto.getFromDate(),attendanceDto.getToDate()));
      }
        return criteriaBuilder.and(predicateList.toArray(new Predicate[]{}));
    }
}
