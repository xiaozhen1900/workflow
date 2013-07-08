package cn.wsn.framework.workflow.util;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 分页工具
 * 
 * @author henryyan
 */
public class PageUtil {

  public static int PAGE_SIZE = 15;

  public static int[] init(Page<?> page, HttpServletRequest request) {
    int pageNumber = Integer.parseInt(StringUtils.defaultIfBlank(request.getParameter("pageNum"), "1"));
    page.setPageNo(pageNumber);
    int pageSize = Integer.parseInt(StringUtils.defaultIfBlank(request.getParameter("pageSize"), String.valueOf(PAGE_SIZE)));
    page.setPageSize(pageSize);
    int firstResult = page.getFirst() - 1;
    int maxResults = page.getPageSize();
    return new int[] {firstResult, maxResults};
  }

}
