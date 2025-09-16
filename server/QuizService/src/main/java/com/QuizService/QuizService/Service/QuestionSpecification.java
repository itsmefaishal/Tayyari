package com.questionService.questions.Service;

import com.questionService.questions.Entity.Question;
import com.questionService.questions.QuestionDTO.QuestionDTO;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class QuestionSpecification {
	//added for searching questions

    public static Specification<Question> filterBy(QuestionDTO dto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (dto.getQuestionContent() != null) {
                predicates.add(cb.like(root.get("QuestionContent"), "%" + dto.getQuestionContent() + "%"));
            }
            if (dto.getCorrectAns() != null) {
                predicates.add(cb.equal(root.get("correctAns"), dto.getCorrectAns()));
            }
            if (dto.getSubject() != null) {
                predicates.add(cb.equal(root.get("subject"), dto.getSubject()));
            }
            if (dto.getCategory() != null) {
                predicates.add(cb.equal(root.get("category"), dto.getCategory()));
            }
            if (dto.getSubCat() != null) {
                predicates.add(cb.equal(root.get("subCat"), dto.getSubCat()));
            }
            if (dto.getMultipleChoice() != null) {
                predicates.add(cb.equal(root.get("multipleChoice"), dto.getMultipleChoice()));
            }
            if (dto.getDifficulty() != null) {
                predicates.add(cb.equal(root.get("difficulty"), dto.getDifficulty()));
            }
            if (dto.getMarks() != null) {
                predicates.add(cb.equal(root.get("marks"), dto.getMarks()));
            }
            if (dto.getNegativeMarks() != null) {
                predicates.add(cb.equal(root.get("negativeMarks"), dto.getNegativeMarks()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

