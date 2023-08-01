package project.greg.TodoList.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.greg.TodoList.model.dao.RateDao;
import project.greg.TodoList.model.dao.UserDao;
import project.greg.TodoList.model.entity.Rate;
import project.greg.TodoList.model.entity.User;

@Service
public class RateService {
    @Autowired
    RateDao rateDao;
    @Autowired
    UserDao userDao;

    public void createRate(Integer id, Integer rate) {
        User user = userDao.findById(id).get();
        Rate newRate = new Rate();
        newRate.setRate(rate);
        newRate.setUser(user);
        rateDao.save(newRate);
    }
}
