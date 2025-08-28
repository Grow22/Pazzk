

const bookmarkButtons = document.querySelectorAll(".bookmark-on");


const isLoggedIn = document.body.dataset.loggedIn === "true";

bookmarkButtons.forEach((button) => {
  button.addEventListener("click", () => {

    // 로그인 상태가 아닐 시 함수 종료
    if(!isLoggedIn) {
        alert("로그인 후 사용 가능합니다");
        return;
    }
    const itemId = button.dataset.itemId;
    button.disabled = true; // 중복 클릭 방지

    sendToServer(itemId, button);
  });
});

const sendToServer = async (itemId, button) => {
  try {
    console.log(itemId);
    const response = await fetch(`/bookmark/${itemId}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
    });


    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(`서버 응답 실패: ${response.status} - ${errorText}`);
    }

console.log(response);
    const result = await response.json();
    console.log(`즐겨찾기 성공:`, result);
    // 성공 시 버튼 비활성화 상태 유지
    button.disabled = true;
  } catch (error) {
    console.error(`즐겨찾기 요청 중 에러 발생: ${error.message}`);
    // 실패 시 버튼을 다시 활성화하여 재시도 가능하게 함
    button.disabled = false;
  }
};