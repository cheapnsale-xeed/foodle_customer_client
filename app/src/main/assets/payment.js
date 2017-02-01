/* 이미 script 태그선언이 밖에서 된거라서 별다른 선언 없이 바로 function을 기술하면됨*/
function call_payment(merchantId, amount, menuName) {
    var IMP = window.IMP; // 생략가능
    IMP.init('imp93718895'); // 'iamport' 대신 부여받은 "가맹점 식별코드"를 사용

    //onclick, onload 등 원하는 이벤트에 호출합니다
    IMP.request_pay({
        pg : 'inicis', // version 1.1.0부터 지원.
        pay_method : 'card',
        merchant_uid : merchantId,
        name : menuName,
        amount : amount,
        buyer_email : 'iamport@siot.do',
        buyer_name : '구매자이름',
        buyer_tel : '010-1234-5678',
        buyer_addr : '서울특별시 강남구 삼성동',
        buyer_postcode : '123-456',
        m_redirect_url : 'https://cv47nyx5yl.execute-api.ap-northeast-1.amazonaws.com/prod/payments/complete',
        app_scheme : 'iamportapp'
    }, function(rsp) {

    });
}
