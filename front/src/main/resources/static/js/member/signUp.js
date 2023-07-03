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
            return response.json()
        }).then(data => {
            if(!data.result){
                alert('사용 가능한 닉네임 입니다.')
                inputNickname.readOnly = true;
                checkNicknameBtn.disabled = true;
            } else{
                alert('이미 사용중인 닉네임 입니다.');
            }
        })
    } else{
        alert('숫자, 영어, 한국어, 언더스코어(_)만을 허용하며 최소 2자 이상 15자 이하의 닉네임만 허용합니다.')
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

            return response.json();
        }).then(data =>{
            if(!data.result){
                alert('사용 가능한 이메일 입니다.')
                inputEmail.readOnly=true;
                checkEmailBtn.disabled = true;
            }else{
                alert('이미 사용 중인 이메일 입니다.')
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
            return response.json()
        }).then(data => {
                if(!data.result){
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

function checkLoginId(){
    let inputLoginId = document.getElementById("id");
    let checkLoginIdBtn = document.getElementById("checkLoginIdBtn");
    let loginIdVal = inputLoginId.value;

    const url = `/checkLoginId/${loginIdVal}`;

    let regExp = /^[a-z]+[a-z0-9]{5,19}$/g;
    let emptyRegex = /\s/g;

    if(regExp.test(loginIdVal) && ! emptyRegex.test(loginIdVal)){
        fetch(url, {
            Accept: "application/json",
            method:"GET"
        }).then((response) => {
            return response.json()
        }).then(data => {
            if(!data.result){
                //사용 가능한 경우 input창은 고치지 못하게 바꾸고 버튼도 누르지 못하게 바꾼다,.
                alert('사용 가능한 회원 아이디입니다.')
                inputLoginId.readOnly=true;
                checkLoginIdBtn.disabled = true;
            }else{
                alert('이미 사용중인 회원 로그인 아이디입니다.')
            }
        });
    } else{
        if (emptyRegex.test(loginIdVal)){
            alert('회원 아이디에 공백 입력은 불가합니다.')
        } else{
            alert('회원 아이디는 영어로 시작해야하며 숫자를 포함해 5~19 글자 이내로 작성해주세요.')
        }
    }

}


/**
 * 들어오는 휴대 전화 번호를 양식에 맞게 하이픈(-) 추가해주는 작업
 * */
const autoHyphen = (target) => {
    target.value = target.value
        .replace(/[^0-9]/g, '')
        .replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3").replace(/(\-{1,2})$/g, "");
}

