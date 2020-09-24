package com.warmer.kgmaker.controller;

import com.warmer.kgmaker.entity.SGNode;
import com.warmer.kgmaker.repository.SGNodeReponsitory;
import com.warmer.kgmaker.util.R;
import com.warmer.kgmaker.util.TestUtil;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.StartNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

@RestController
@RequestMapping("/sg")
public class SGNodeController {

    @Autowired
    SGNodeReponsitory sgNodeReponsitory;

    @DeleteMapping("/delete")
    public String delete() {
        sgNodeReponsitory.deleteAll();
        return "OK";
    }

    @GetMapping("/add")
    public String add() {
        addNodes();
        return "OK";
    }

    /*@GetMapping("/get")
    public String relation() {
        SGNode node = sgNodeReponsitory.findByName("tsp");
        List<Long> ids = new ArrayList<>();
        ids.add(node.getId());
        Iterable<SGNode> result = sgNodeReponsitory.findAllById(ids, 1);
        return "OK";
    }*/

    @GetMapping("/get")
    public R<Map<String, Object>> relation() {

        /*SGNode node = sgNodeReponsitory.findByName("tsp");
        List<Long> ids = new ArrayList<>();
        ids.add(node.getId());
        Iterable<SGNode> result = sgNodeReponsitory.findAllById(ids, 1);*/
        R<Map<String, Object>> r = new R<>();
        try {
            Iterable<SGNode> result = sgNodeReponsitory.findAll();
            List<SGNode> list = new ArrayList<>();
            Iterator<SGNode> it = result.iterator();
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

    @GetMapping("/getAll")
    public Iterable<SGNode> getAll() {
        return sgNodeReponsitory.findAll();
    }


    private void addNodes() {
        sgNodeReponsitory.deleteAll();

        List<SGNode> list = new ArrayList<>();

        SGNode node = new SGNode("tsp");
        list.add(node);

        for (Integer i = 1; i <= 10; i++) {
            node = new SGNode("tsp" + i);
            node.setCount(new Random().nextLong());
            node.setError(new Random().nextLong());
            node.setMax(new Random().nextDouble());
            node.setMin(new Random().nextDouble());
            list.add(node);
        }

        sgNodeReponsitory.saveAll(list);

        SGNode start = sgNodeReponsitory.findByName("tsp1");
        SGNode end = sgNodeReponsitory.findByName("tsp");
        start.addCalls(end, new Random().nextLong());
        sgNodeReponsitory.save(start);

        start = sgNodeReponsitory.findByName("tsp2");
        end = sgNodeReponsitory.findByName("tsp");
        start.addCalls(end, new Random().nextLong());
        sgNodeReponsitory.save(start);

        start = sgNodeReponsitory.findByName("tsp9");
        end = sgNodeReponsitory.findByName("tsp7");
        start.addCalls(end, new Random().nextLong());
        sgNodeReponsitory.save(start);

        start = sgNodeReponsitory.findByName("tsp7");
        end = sgNodeReponsitory.findByName("tsp2");
        start.addCalls(end, new Random().nextLong());
        sgNodeReponsitory.save(start);

        start = sgNodeReponsitory.findByName("tsp2");
        end = sgNodeReponsitory.findByName("tsp8");
        start.addCalls(end, new Random().nextLong());
        sgNodeReponsitory.save(start);

        start = sgNodeReponsitory.findByName("tsp");
        end = sgNodeReponsitory.findByName("tsp3");
        start.addCalls(end, new Random().nextLong());
        sgNodeReponsitory.save(start);

        start = sgNodeReponsitory.findByName("tsp");
        end = sgNodeReponsitory.findByName("tsp4");
        start.addCalls(end, new Random().nextLong());
        sgNodeReponsitory.save(start);

        start = sgNodeReponsitory.findByName("tsp6");
        end = sgNodeReponsitory.findByName("tsp3");
        start.addCalls(end, new Random().nextLong());
        sgNodeReponsitory.save(start);

        start = sgNodeReponsitory.findByName("tsp3");
        end = sgNodeReponsitory.findByName("tsp5");
        start.addCalls(end, new Random().nextLong());
        sgNodeReponsitory.save(start);

        start = sgNodeReponsitory.findByName("tsp5");
        end = sgNodeReponsitory.findByName("tsp10");
        start.addCalls(end, new Random().nextLong());
        sgNodeReponsitory.save(start);
    }
}

