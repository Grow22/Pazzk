  const likeButtons = document.querySelectorAll(".comment-like");

    likeButtons.forEach((button) => {
        button.addEventListener("click", () => {
            const itemId = button.dataset.itemId;
            sendLikeToServer(itemId);
        });
    });

    const sendLikeToServer = (itemId) => {
        fetch(`/likes/${itemId}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
        })
        .then((response) => {
            if (!response.ok) {
                throw new Error("네트워크 응답에 실패했습니다.");
            }
            return response.json();
        })
        .then((result) => {
            console.log(`좋아요 성공: ${result.like}`);
            const likeButton = document.querySelector(`.comment-like[data-item-id='${itemId}']`);
            if (likeButton) {
                likeButton.textContent = `좋아요(${result.like})`;
            }
        })
        .catch((error) => {
            console.error(`좋아요 요청 오류 발생: ${error}`);
        });
    };