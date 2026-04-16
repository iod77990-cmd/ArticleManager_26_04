package org.example.ArticleManager;

import org.example.dto.Article;
import org.example.controller.ArticleController;
import org.example.controller.Controller;
import org.example.controller.MemberController;
import org.example.dto.Member;

import java.util.Scanner;

public class App {

    public void run(){
        Scanner sc = new Scanner(System.in);

        System.out.println("== 프로그램 시작 ==");

        MemberController memberController = new MemberController(sc);
        ArticleController articleController = new ArticleController(sc);

        articleController.makeTestData();
        memberController.makeTestMember();

        Controller controller = null;

        while (true) {
            System.out.print("명령어 > ");
            String cmd = sc.nextLine().trim();

            if(cmd.equals("member profile")){
                if(loginedMember.Id == Member.getId()){
                    System.out.println("이름" + Member.getName());
                    System.out.println("아이디" + Member.getId());
                    System.out.println("작성한 글" + Article.getTitle());
                    System.out.println("작성한 내용" + Article.getBody());
                }
            }

            if(cmd.equals("member modify")){


            }

            if(cmd.equals("member quit")){


            }

            if (cmd.equals("exit")) { // exit
                break;
            } else if (cmd.length() == 0) {
                System.out.println("명령어를 입력해주세요");
                continue;
            }

            String[] cmdBits = cmd.split(" ");
            String controllerName = cmdBits[0];
            if(cmdBits.length == 1){
                System.out.println("명령어 확인해");
                continue;
            }
            String actionMethodName = cmdBits[1];

            String forloginChecks = controllerName + "/" + actionMethodName;

            switch (forloginChecks){
                case "article/write":
                case "article/delete":
                case "article/modify":
                case "member/logout":
                    if(Controller.isLogined() == false){
                        System.out.println("로그인을 해 빨리!");
                        continue;
                    }
                    break;
            }

            switch(forloginChecks){
                case "member/login":
                case "member/join":
                  if(Controller.isLogined()) {
                    System.out.println("로그아웃 해 빨리");
                    continue;
                  }
                  break;
            }

            if (controllerName.equals("article")) {
                controller = articleController;
            } else if (controllerName.equals("member")) {
                controller = memberController;
            } else {
                System.out.println("지원하지 않는 기능");
                continue;
            }
            controller.doAction(cmd, actionMethodName);
        }
        System.out.println("== 프로그램 종료 ==");
        sc.close();
    }
}
