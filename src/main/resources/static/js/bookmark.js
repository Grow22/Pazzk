document.addEventListener("DOMContentLoaded", () => {
  const bookmarkButtons = document.querySelectorAll(".bookmark-on");
  const isLoggedIn = document.body.dataset.loggedIn === "true";

  bookmarkButtons.forEach((button) => {
    button.addEventListener("click", () => {
      if (!isLoggedIn) {
        alert("로그인 후 사용 가능합니다");
        return;
      }
      const itemId = button.dataset.itemId;
      button.disabled = true;
      sendToServer(itemId, button);
    });
  });

  const sendToServer = async (itemId, button) => {
    try {
      const response = await fetch(`/bookmark/${itemId}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
      });

      // 이미 즐겨찾기를 누른 경우
      if (response.status === 409) {
        if (confirm("이미 즐겨찾기된 영상입니다. 즐겨찾기를 취소하시겠습니까?")) {
          const deleteResponse = await fetch(`/deletes/${itemId}`, {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
          });

          // 예를 눌렀을 경우
        if(deleteResponse) {
            alert("즐겨찾기가 취소되었습니다.");
        }
          // 북마크 리포지토리에 없을 경우 함수 반환
          else {
            const Text = await deleteResponse.text();
            console.log(Text);
          }

        }
        return;
      }

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`서버 응답 실패: ${response.status} - ${errorText}`);
      }

      const result = await response.json();
      console.log(`즐겨찾기 성공:`, result);

    } catch (error) {
      console.error(`요청 처리 중 오류 발생: ${error.message}`);
    } finally {
      button.disabled = false;
    }
  };
});