package com.kh.spring.notice.controller;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.notice.model.service.NoticeService;
import com.kh.spring.notice.model.vo.Notice;
import com.kh.spring.notice.model.vo.Search;

@Controller
public class NoticeController {
	
	@Autowired
	private NoticeService nService;
	
	// 공지사항 목록 출력
	@RequestMapping("nlist.kh")
	public ModelAndView noticeList(ModelAndView mv) {
		
		ArrayList<Notice> list = nService.selectList();   
		
		if(list != null) {
			mv.addObject("list", list);
			mv.setViewName("notice/noticeListView");
		}else {
			mv.addObject("msg", "공지사항 목록 조회 실패");
			mv.setViewName("common/errorPage");
		}
		
		return mv;
	}
	
	
	// 공지사항 등록 화면
	@RequestMapping("nWriterView.kh")
	public String nWiterView() {
		return "notice/noticeWriteForm";
	}
	
	
	// 공지사항 등록하기
	@RequestMapping("ninsert.kh")
	public String noticeInsert(Notice notice, 
		HttpServletRequest request,Model model,
		@RequestParam(name="uploadFile", required=false) MultipartFile uploadFile )  {
		/* @RequestParam
		 * name[또는 value] : 전달받을 파라미터의 name속성 값
		 * 
		 * required : 해당 파라미터가 필수인지의 여부(true : 필수)
		 *   -> required=true일 때 NULL 입력 시 400에러 발생
		 * 
		 * defaultValue : 해당 파라미터의 기본값
		 * */
		
		System.out.println(notice);
		System.out.println(uploadFile.getOriginalFilename());
		
		// 업로된 파일을 서버에 저장하는 작업
		if(!uploadFile.getOriginalFilename().equals("")) {
			
			String filePath = saveFile(uploadFile, request);
			
			if(filePath != null) {
				// DB에 저장할 파일 세팅
				notice.setFilePath(uploadFile.getOriginalFilename());
			}
		}
		
		int result = nService.insertNotice(notice);
		
		
		if(result > 0) {
			return "redirect:nlist.kh";
		}else {
			deleteFile(uploadFile.getOriginalFilename(), request);
			model.addAttribute("msg","공지사항 등록 실패");
			return "common/errorPage";
		}
	}
	
	
	// 파일 저장 메소드
	public String saveFile(MultipartFile file, 
						   HttpServletRequest request) {
		
		// 파일 저장 경로 설정
		String root 
			= request.getSession().getServletContext().getRealPath("resources");
		
		String savePath = root + "\\nuploadFiles";
		
		System.out.println("savePath : " + savePath);
		
		// 저장 폴더 선택
		File folder = new File(savePath);
		
		// 만약 폴더가 없을 경우 자동 생성 시키기
		if(!folder.exists()) {
			folder.mkdir(); 
		}
		
		
		String filePath 
			= folder + "\\" 
			  + file.getOriginalFilename();
		
		// 공지사항 게시판은 관리자가 관리함
		// -> 관리자가 업로드할 파일을 별도로 규칙성 있게 
		//    관리하기 때문에 파일을 원본 이름 그대로 저장하겠음.
		
		try {
			file.transferTo(new File(filePath));
			// 이 때 파일 저장됨.
		}catch(Exception e) {
			System.out.println("파일 전송 에러  " + e.getMessage());
		}
		
		return filePath;
	}
	
	
	// 파일 삭제 메소드
	//  공지글 등록 실패 또는 글 수정으로 업로드 파일이 변한 경우
	//  저장되어있는 기존 파일 삭제
	public void deleteFile(String fileName, 
						HttpServletRequest request) {
		// 파일 저장 경로 설정
		String root 
			= request.getSession().getServletContext().getRealPath("resources");
		
		String savePath = root + "\\nuploadFiles";
		
		// 삭제할 파일 경로 + 파일명
		File deleteFile = new File(savePath + "\\" + fileName);
		
		// 해당 파일이 존재할 경우 삭제
		if(deleteFile.exists()) {
			deleteFile.delete();
		}
		
	}
	
	
	@RequestMapping("ndetail.kh")
	public String noticeDetail(int nId, Model model) {
		System.out.println("nId : " + nId);
		Notice notice = nService.selectOne(nId);
		System.out.println(notice);
		if(notice != null) { // 게시글이 존재하는 경우
			model.addAttribute("notice", notice);
			return "notice/noticeDetailView";
		}else {
			model.addAttribute("msg", "공지사항 상세조회 실패");
			return "common/errorPage";
		}
		
	}
	
	
	@RequestMapping("nupView.kh")
	public String noticeUpdateView(int nId, Model model) {
		model.addAttribute("notice", nService.selectOne(nId));
		
		return "notice/noticeUpdateView";
	}
	
	
	
	@RequestMapping(value="nupdate.kh", method=RequestMethod.POST)
	public String noticeUpdate(Notice notice, Model model,
							   HttpServletRequest request,
							   MultipartFile reloadFile) {
		
		// 새로 업로드된 파일이 있을 경우
		if(reloadFile != null && !reloadFile.isEmpty()) {
			
			// 기존 업로드 파일이 있을 경우
			if(notice.getFilePath() != null) { 

				// 기존 파일 삭제
				deleteFile(notice.getFilePath(), request);
			}
			
			// 새로 업로드된 파일 저장
			String savePath = saveFile(reloadFile, request);
			
			// 새로운 파일이 잘 저장이 되었다면
			if(savePath != null) {
				notice.setFilePath(reloadFile.getOriginalFilename());
			}else {
				notice.setFilePath(null);
			}
			
		}
			
		int result = nService.updateNotice(notice);
			
		if (result > 0) {
			return "redirect:ndetail.kh?nId="+notice.getnId();
		} else {
			model.addAttribute("msg", "공지사항 수정 실패");
			return "common/errorPage";
		}	
	}
	
	
	// 공지사항 삭제
	// 1. DB 데이터 삭제
	// 2. DB 데이터 삭제 성공 시 저장된 파일 삭제
	// 3. 삭제 후 공지사항 목록으로 redirect 
	@RequestMapping("ndelete.kh")
	public String noticeDelete(int nId, Model model, 
								HttpServletRequest request,
								RedirectAttributes rd) {

		// 파일 지우기 위해 nId의 공지사항 조회
		Notice notice = nService.selectOne(nId); 
		
		int result = nService.deleteNotice(nId); 

		
		if (result > 0) {
			// 해당 공지사항에 첨부파일이 존재했을 경우
			if (notice.getFilePath() != null) { 
				deleteFile(notice.getFilePath(), request);
			}

			rd.addFlashAttribute("msg", "게시글 삭제 성공");
			return "redirect:nlist.kh";
		} else {
			model.addAttribute("msg", "공지사항 수정 실패");
			return "common/errorPage";
		}
	}
	
	// 검색
	@RequestMapping("nsearch.kh")
	public String noticeSearch(Search search, Model model){
		
		System.out.println(search.getSearchCondition());
		System.out.println(search.getSearchValue());
		System.out.println(search.getExistFile());
		// 체크 O : on
		// 체크 X : null
		
		ArrayList<Notice> searchList 
			= nService.searchList(search);
		
		for(Notice n : searchList) {
			System.out.println(n);
		}
		
		
		model.addAttribute("list", searchList);
		model.addAttribute("search", search);
		return "notice/noticeListView";
	}
	
	
}








