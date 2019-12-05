package com.shemesh.services;

import com.shemesh.dao.QuestionActionsDao;
import com.shemesh.models.Requests.QuestionActionsRequest;
import com.shemesh.utils.DbHelper;
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
    private QuestionActionsDao dao;

    @Autowired
    private DbHelper dbHelper;


    public void commentQuestion(QuestionActionsRequest request) throws SQLException {
        dbHelper.executeQuery(dao.commentQuestionQuery(request));
    }

    public void hideQuestion(QuestionActionsRequest request) throws SQLException {
        dbHelper.executeQuery(dao.hideQuestionQuery(request));

    }

    public void markQuestion(QuestionActionsRequest request) throws SQLException {
        dbHelper.executeQuery(dao.markQuestionQuery(request));
    }

    public void feedbackQuestion(QuestionActionsRequest request) throws SQLException {
        dbHelper.executeQuery(dao.feedbackQuestionQuery(request));

    }
}
