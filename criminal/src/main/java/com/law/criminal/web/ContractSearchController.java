package com.law.criminal.web;

import com.law.criminal.comm.TrafficTableOutput;
import com.law.criminal.model.elasticsearch.ContractLaw;
import com.law.criminal.repository.elasticsearch.ContractLawSearchRepository;
import com.law.criminal.repository.mysql.LawSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/contractsearch")
public class ContractSearchController {
    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ContractLawSearchRepository contractLawSearchRepository;

    @Autowired
    LawSearchRepository lawSearchRepository;

    @RequestMapping("/getContractLawByChapterNum")
    public Page<ContractLaw> getContractLawByChapterNum(int chapterNum, int pageSize, int pageNum) {
        logger.info("getContractLawByChapterNum,getContractLawByChapterNum:{},{},{}",chapterNum,pageSize,pageNum);
        Pageable pageable = PageRequest.of(pageNum,pageSize,new Sort(Sort.Direction.ASC,"itemNum"));
        return contractLawSearchRepository.findContractLawByChapterNum(chapterNum,pageable);
    }

    @RequestMapping("/getContractLawByChapterNumAndSectionNum")
    public Page<ContractLaw> getContractLawByChapterNumAndSectionNum(int chapterNum, int sectionNum, int pageSize, int pageNum) {
        logger.info("ContractSearchController,getContractLawByChapterNumAndSectionNum:{},{},{},{}",chapterNum,sectionNum,pageSize,pageNum);
        Pageable pageable = PageRequest.of(pageNum,pageSize,new Sort(Sort.Direction.ASC,"itemNum"));
        return contractLawSearchRepository.findContractLawByChapterNumAndSectionNum(chapterNum,sectionNum,pageable);
    }


    @RequestMapping("/getContractLawByQueryString")
    public Page<ContractLaw> getContractLawByQueryString(String queryString, int pageSize, int pageNum) {
        logger.info("ContractSearchController,getContractLawByQueryString:{},{},{}",queryString,pageSize,pageNum);
        List<Sort.Order> orders=new ArrayList< Sort.Order>();
        orders.add( new Sort.Order(Sort.Direction.DESC, "_score"));
        orders.add( new Sort.Order(Sort.Direction.ASC, "itemNum"));
        Pageable pageable = PageRequest.of(pageNum,pageSize,new Sort(orders));
        return contractLawSearchRepository.getContractLawByQueryString(queryString,pageable);
    }

//    @RequestMapping("/test")
//    public Page<TrafficLawNew> getTrafficLawNewByQueryString(String queryString, int pageSize, int pageNum) {
//        logger.info("TrafficSearchController,getTrafficLawByQueryString:{},{},{}",queryString,pageSize,pageNum);
//        return trafficLawSearchService.getTrafficLawNewByQueryString(queryString,pageSize,pageNum);
//    }

    @RequestMapping("/getLawTable")
    public List<TrafficTableOutput> getLawTable()
    {
        return lawSearchRepository.getContractLawTable();
    }
}
