package com.warmer.kgmaker.controller;

import com.warmer.kgmaker.entity.test.Person;
import com.warmer.kgmaker.entity.test.PersonRelation;
import com.warmer.kgmaker.repository.PersonReponsitory;
import com.warmer.kgmaker.util.R;
import com.warmer.kgmaker.util.TestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestNodeController {

    @Autowired
    private PersonReponsitory personReponsitory;

    @GetMapping("/get")
    public R<Map<String, Object>> get() {
        R<Map<String, Object>> r = new R<>();
        try {
            Iterable<Person> result = personReponsitory.findAll();
            List<Person> list = new ArrayList<>();
            Iterator<Person> it = result.iterator();
            while (it.hasNext()) {
                list.add(it.next());
            }

            Map<String, Object> graphData = TestUtil.convert(list);
            r.setCode(200);
            r.setData(graphData);
        } catch (Exception e) {
            e.printStackTrace();
            r.setCode(500);
            r.setMsg("服务器错误");
        }
        return r;
    }

    @GetMapping("/add")
    public void add() {

        Person person = null;
        List<Person> personList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            person = new Person();
            person.setName("p" + i);
            personList.add(person);
        }
        personReponsitory.saveAll(personList);

        Person start = personReponsitory.findByName("p1");
        Person end = personReponsitory.findByName("p2");
        PersonRelation personRelation = new PersonRelation();
        personRelation.setName("一般朋友");
        personRelation.setStartNode(start);
        personRelation.setEndNode(end);
        start.getRelations().add(personRelation);
        personReponsitory.save(start);

        start = personReponsitory.findByName("p1");
        end = personReponsitory.findByName("p3");
        personRelation = new PersonRelation();
        personRelation.setName("死党");
        personRelation.setStartNode(start);
        personRelation.setEndNode(end);
        start.getRelations().add(personRelation);
        personReponsitory.save(start);

        start = personReponsitory.findByName("p1");
        end = personReponsitory.findByName("p4");
        personRelation = new PersonRelation();
        personRelation.setName("一般朋友");
        personRelation.setStartNode(start);
        personRelation.setEndNode(end);
        start.getRelations().add(personRelation);
        personReponsitory.save(start);

        start = personReponsitory.findByName("p2");
        end = personReponsitory.findByName("p5");
        personRelation = new PersonRelation();
        personRelation.setName("friend1");
        personRelation.setStartNode(start);
        personRelation.setEndNode(end);
        start.getRelations().add(personRelation);
        personReponsitory.save(start);

        start = personReponsitory.findByName("p2");
        end = personReponsitory.findByName("p6");
        personRelation = new PersonRelation();
        personRelation.setName("friend1");
        personRelation.setStartNode(start);
        personRelation.setEndNode(end);
        start.getRelations().add(personRelation);
        personReponsitory.save(start);

        start = personReponsitory.findByName("p3");
        end = personReponsitory.findByName("p7");
        personRelation = new PersonRelation();
        personRelation.setName("friend1");
        personRelation.setStartNode(start);
        personRelation.setEndNode(end);
        start.getRelations().add(personRelation);
        personReponsitory.save(start);

        start = personReponsitory.findByName("p3");
        end = personReponsitory.findByName("p8");
        personRelation = new PersonRelation();
        personRelation.setName("friend1");
        personRelation.setStartNode(start);
        personRelation.setEndNode(end);
        start.getRelations().add(personRelation);
        personReponsitory.save(start);

        start = personReponsitory.findByName("p4");
        end = personReponsitory.findByName("p9");
        personRelation = new PersonRelation();
        personRelation.setName("friend1");
        personRelation.setStartNode(start);
        personRelation.setEndNode(end);
        start.getRelations().add(personRelation);
        personReponsitory.save(start);

        start = personReponsitory.findByName("p4");
        end = personReponsitory.findByName("p10");
        personRelation = new PersonRelation();
        personRelation.setName("friend1");
        personRelation.setStartNode(start);
        personRelation.setEndNode(end);
        start.getRelations().add(personRelation);
        personReponsitory.save(start);

    }

}
