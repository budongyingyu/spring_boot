package com.li.test.spring_boot.readinglist;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;

@Controller
@RequestMapping("/reader")
//prefix 属性说明应该注入带 amazon 前缀的属性
@ConfigurationProperties(prefix="amazon")
public class ReadingListController {

//    private Logger logger = Logger.getLogger(ReadingListController.class);

    private ReadingListRepository readingListRepository;

    private AmazonProperties amazonProperties;

    @Autowired
    public ReadingListController(ReadingListRepository readingListRepository,
                                            AmazonProperties amazonProperties) {
        this.readingListRepository = readingListRepository;
        this.amazonProperties = amazonProperties;
    }
    @RequestMapping(value="/{reader}", method=RequestMethod.GET)
    public String readersBooks(@PathVariable("reader") String reader,Model model) {
//        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>");
        List<Book> readingList = readingListRepository.findByReader(reader);
        if (readingList != null) {
            model.addAttribute("associateId", amazonProperties.getAssociateId());
            model.addAttribute("books", readingList);
        }
        return "readingList";
    }
    @RequestMapping(value="/{reader}", method=RequestMethod.POST)
    public String addToReadingList(@PathVariable("reader") String reader, Book book) {
        book.setReader(reader);
        readingListRepository.save(book);
        return "redirect:/{reader}";
    }

}
