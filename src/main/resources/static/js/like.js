document.addEventListener('DOMContentLoaded', () => {
    // 1. 모든 '.comment-like' 버튼을 선택합니다.
    const likeButtons = document.querySelectorAll(".comment-like");

    // login 상태를 가져 와 로그인 시 좋아요 기능 호출
    const isLoggedIn = document.body.dataset.loggedIn === "true";

    // 2. 각 버튼에 클릭 이벤트 리스너를 추가합니다.
    likeButtons.forEach(button => {
        button.addEventListener("click", () => {

            console.log(isLoggedIn);
            // 로그인 상태가 아닐 시 종료
            if(!isLoggedIn) {
                alert("로그인 후 사용 가능합니다");
                return;
            }
            // 중복 클릭 방지를 위해 버튼을 비활성화합니다.
            button.disabled = true;

            const itemId = button.dataset.itemId;

            sendLikeToServer(itemId, button);
        });
    });

    // 3. 서버에 좋아요 요청 전송
    const sendLikeToServer = async (itemId, button) => {
        try {
            const response = await fetch(`/likes/${itemId}`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
            });

            console.log(response);
            //응답 상태가 409(좋아요를 이미 눌렀을 시) 함수 종료
            if(response.status === 409) {
                console.log("좋아요를 다음 날");
                alert("이미 오늘 좋아요를 눌렀습니다.");
                return;
            }

            if (!response.ok) {
                // 서버가 200 OK 외의 응답(예: 404, 500)을 보낼 때
                const errorText = await response.text();
                throw new Error(`서버 응답 실패: ${response.status} - ${errorText}`);
            }

            const result = await response.json();

            // 4. 성공 시, 버튼 텍스트를 업데이트합니다.
            console.log(`좋아요 성공: ${result.likes}`);
            button.textContent = `좋아요(${result.likes})`;

        } catch (error) {
            // 5. 네트워크 오류나 서버 오류 시 에러를 콘솔에 출력합니다.


            console.error(`좋아요 요청 중 오류 발생: ${error.message}`);

        } finally {
            // 6. 요청이 완료되면 버튼을 다시 활성화합니다.
            button.disabled = false;
        }
    };
});