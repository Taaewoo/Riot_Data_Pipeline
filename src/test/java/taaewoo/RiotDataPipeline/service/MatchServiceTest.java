package taaewoo.RiotDataPipeline.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;


public class MatchServiceTest {

    MatchService matchService;

    @BeforeEach
    public void beforeEach(){
        matchService = new MatchService();
    }

    @Test
    void getMatchInfoByMatchID() {

//        String result = matchService.callRiotApiMatchInfoByMatchID("KR_5850874996");
//        System.out.println(result);
//        Assertions.assertThat(result).isNotEqualTo(null);



//        //given
//        Member member = new Member();
//        member.setName("hello");
//
//        //when
//        Long saveId = memberService.join(member);
//
//        //then
//        Member result = memberService.findOne(saveId).get();
//        Assertions.assertThat(member.getName()).isEqualTo(result.getName());
    }
}
