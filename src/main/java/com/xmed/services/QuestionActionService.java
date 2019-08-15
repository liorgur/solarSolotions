package com.xmed.services;

import com.xmed.models.Requests.QuestionActionsRequest;
import com.xmed.utils.DbHelper;
import com.xmed.dao.QuestionActionsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * @author Lior Gur
 */
@Service
@Slf4j
public class QuestionActionService {

    @Autowired
    QuestionActionsDao dao;

    @Autowired
    DbHelper dbHelper;


    public void commentQuestion(QuestionActionsRequest request) throws SQLException {
        dbHelper.executeQuery(dao.commentQuestionQuery(request));
    }
}
