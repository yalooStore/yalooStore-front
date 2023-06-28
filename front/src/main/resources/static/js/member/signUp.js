function checkNickname(){
    let inputNickname = document.getElementById("nickname");
    let checkNicknameBtn = document.getElementById("checkNicknameBth");

    let nicknameValue = inputNickname.value;

    let nicknameRegex = /^[가-힣ㄱ-ㅎa-zA-Z0-9._-]{2,15}$/;
    let emptyRegex = /\s/g;
    if(nicknameRegex.test(nicknameValue) && !emptyRegex.test(nicknameValue)){
        const url = `/checkNickname/${nicknameValue}`;
        fetch(url, {
            Accept: "application/json",
            method:"GET"
        }).then((response) => {
            // API 서버에서 넘어오는 result 값이 false면 해당 값을 가진 회원이 없음 중복없음을 뜻함
            if(!response.result){
                alert('사용 가능한 닉네임 입니다.')
                inputNickname.readOnly=true;
                checkNicknameBtn.disabled = true;
            }else{
                alert('이미 사용 중인 닉네임 입니다.')
            }
        });
    } else{
        alert('숫자, 영어, 한국어와 언더스코어(_)만을 허용하며 최소 2자 이상의 15자 이하의 닉네임만 가능합니다.');
    }

}

function checkEmail(){
    let inputEmail = document.getElementById("email");
    let checkEmailBtn = document.getElementById("checkEmailBth");
    let emailValue = inputEmail.value;


    let emailRegex = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;
    let emptyRegex = /\s/g;
    if(emailRegex.test(emailValue) && !emptyRegex.test(emailValue)){
        const url= `/checkEmail/${emailValue}`;
        fetch(url, {
            Accept: "application/json",
            method:"GET"
        }).then((response) => {
            // API 서버에서 넘어오는 result 값이 false면 해당 값을 가진 회원이 없음 중복없음을 뜻함
            if(!response.result){
                alert('사용 가능한 닉네임 입니다.')
                inputEmail.readOnly=true;
                checkEmailBtn.disabled = true;
            }else{
                alert('이미 사용 중인 닉네임 입니다.')
            }
        });
    }else{
        alert('이메일 형식에 맞게 작성해주세요.')
    }
}

function checkPhoneNumber(){
    let inputPhoneNumber = document.getElementById("phoneNumber");
    let checkPhoneNumberBtn = document.getElementById("checkPhoneNumberBtn");
    let phoneNumberValue = inputPhoneNumber.value;
    const trimmedPhoneNumber = phoneNumberValue.replace(/-/g, '');

    console.log(phoneNumberValue);
    console.log(trimmedPhoneNumber);

    const url = `/checkPhone/${phoneNumberValue}`;

    let phoneRegex = /^01([0|1])\d{4}\d{4}$/;
    let emptyRegex = /\s/g;
    if(phoneRegex.test(trimmedPhoneNumber) && ! emptyRegex.test(trimmedPhoneNumber)){

        fetch(url, {
            Accept: "application/json",
            method:"GET"
        }).then((response) => {
            // API 서버에서 넘어오는 result 값이 false면 해당 값을 가진 회원이 없음 중복없음을 뜻함
            if(!response.result){
                //사용 가능한 경우 input창은 고치지 못하게 바꾸고 버튼도 누르지 못하게 바꾼다,.
                alert('사용 가능한 휴대전화 번호입니다.')
                inputPhoneNumber.readOnly=true;
                checkPhoneNumberBtn.disabled = true;
            }else{
                alert('이미 사용 중인 휴대전화 번호입니다.')
            }
        });
    } else{
        alert('휴대전화 번호 형식에 맞게 작성해주세요.')
    }

}

const autoHyphen = (target) => {
    target.value = target.value
        .replace(/[^0-9]/g, '')
        .replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3").replace(/(\-{1,2})$/g, "");
}

