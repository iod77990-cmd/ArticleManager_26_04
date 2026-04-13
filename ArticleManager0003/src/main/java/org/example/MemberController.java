package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberController {

    Scanner sc;

    int lastMemberId = 3;
    static List<Member> members;

    public MemberController(Scanner sc){
        this.sc = sc;
        members = new ArrayList<>();
    }

    public void doJoin(){

        System.out.println("== 회원 가입 ==");
        int id = lastMemberId + 1;
        String loginId = null;
        while (true) {
            System.out.print("로그인 아이디 : ");
            loginId = sc.nextLine().trim();
            if (isJoinableLoginId(loginId) == false) {
                System.out.println("이미 사용중인 loginId");
                continue;
            }
            break;
        }

        String password = null;
        while (true) {
            System.out.print("비밀번호 : ");
            password = sc.nextLine().trim();
            System.out.print("비밀번호 확인 : ");
            String passwordConfirm = sc.nextLine().trim();
            if (password.equals(passwordConfirm) == false) {
                System.out.println("비밀번호를 확인하세요.");
                continue;
            }
            break;
        }
        System.out.print("이름 : ");
        String name = sc.nextLine().trim();
        String regDate = Util.getNowStr();
        String updateDate = Util.getNowStr();

        Member member = new Member(lastMemberId, regDate, updateDate, loginId, password, name);
        members.add(member);
        System.out.println(lastMemberId + "번 회원이 가입 되었습니다.");
        lastMemberId++;
    }

    private static boolean isJoinableLoginId(String loginId) {
        for (Member member : members) {
            if (member.getLoginId().equals(loginId)) {
                return false;
            }
        }
        return true;
    }

    static void makeTestMember() {
        System.out.println("== 테스트 회원 생성 ==");
        members.add(new Member(1, Util.getNowStr(), Util.getNowStr(), "test1", "1111", "test1"));
        members.add(new Member(2, Util.getNowStr(), Util.getNowStr(), "test2", "2222", "test2"));
        members.add(new Member(3, Util.getNowStr(), Util.getNowStr(), "test3", "3333", "test3"));

    }

}
