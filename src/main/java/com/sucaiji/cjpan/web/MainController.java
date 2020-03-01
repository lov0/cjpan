package com.sucaiji.cjpan.web;

import com.sucaiji.cjpan.config.Type;
import com.sucaiji.cjpan.model.Index;
import com.sucaiji.cjpan.model.vo.PageVo;
import com.sucaiji.cjpan.service.IndexService;
import com.sucaiji.cjpan.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.sucaiji.cjpan.config.Property.*;


@Controller
public class MainController {
    final static Logger logger= LoggerFactory.getLogger(MainController.class);
    @Autowired
    private IndexService indexService;
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login() {
        if (userService.isEmpty()) {
            return "init";
        }
        return "login";
    }

    @RequestMapping(value = {"/", "index", "/index"})
    public String index(@RequestParam(value = "parent_uuid", defaultValue = ROOT, required = false) String parentUuid,
                        @RequestParam(value = "pg", required = false) Integer pageNumber,
                        @RequestParam(value = "limit", required = false) Integer limit,
                        Model model) {
        if (pageNumber == null) {
            pageNumber = 1;
        }
        Index queryIndex = new Index();
        queryIndex.setParentUuid(parentUuid);
        PageVo vo = indexService.getPageVo(pageNumber, limit, queryIndex);
        Index index = indexService.getIndexByUuid(parentUuid);
        int pageAmount = vo.getPages();
        model.addAttribute("currentPage",pageNumber);
        model.addAttribute("pageAmount",pageAmount);
        model.addAttribute("vo",vo);
        model.addAttribute("parentIndex", index);
        return "index";
    }

    @RequestMapping("/file/{str}")
    public String type(@PathVariable("str") String str,
                       @RequestParam(value = "pg" , required = false) Integer pageNumber,
                       @RequestParam(value = "limit" , required = false) Integer limit,
                       Model model) {
        if (pageNumber == null) {
            pageNumber = 1;
        }
        if (limit == null) {
            limit = 200;
        }
        Type type = Type.getType(str);
        Index index = new Index();
        index.setType(type.toString());
        PageVo vo = indexService.getPageVo(pageNumber, limit, index);
        int pageAmount = vo.getPages();
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("pageAmount", pageAmount);
        model.addAttribute("vo",vo);
        return "type";
    }

    @RequestMapping("/video/{uuid}")
    public String video(@PathVariable("uuid") String uuid,
                        Model model) {
        Index index = indexService.getIndexByUuid(uuid);
        model.addAttribute("index", index);
        return "video";
    }

}
