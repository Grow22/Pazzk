document.addEventListener('DOMContentLoaded', () => {
    // 1. 모든 '.comment-like' 버튼을 선택합니다.
    const likeButtons = document.querySelectorAll(".comment-like");

    // 2. 각 버튼에 클릭 이벤트 리스너를 추가합니다.
    likeButtons.forEach(button => {
        button.addEventListener("click", () => {
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