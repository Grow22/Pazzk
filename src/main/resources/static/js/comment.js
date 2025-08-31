const commentButtons = document.querySelectorAll(".comment-btn");

commentButtons.forEach((button) => {
    button.addEventListener("click", () => {
        const itemId = button.dataset.itemId;
        const commentSection = document.getElementById("comments-" + itemId);

        if (commentSection.style.display === "none" || commentSection.style.display === "") {
            commentSection.style.display = "block";
            // 서버에서 댓글을 불러오는 함수 호출
            loadComments(itemId, commentSection);
        } else {
            commentSection.style.display = "none";
        }
    });
});

/**
 * 서버에서 특정 영상의 댓글을 불러와 화면에 표시하는 함수
 * @param {string} itemId 댓글을 불러올 영상의 ID
 * @param {HTMLElement} commentSection 댓글 목록이 표시될 HTML 요소
 */
const loadComments = (itemId, commentSection) => {
    const commentsList = commentSection.querySelector(".comment-list");
    commentsList.innerHTML = "<div>댓글을 불러오는 중입니다...</div>"; // 로딩 메시지 표시

    // GET 요청을 통해 서버에서 댓글 목록을 가져옴
    fetch(`/comments/${itemId}`)
        .then(response => {
            if (!response.ok) {
                // 서버 응답이 200 OK가 아니면 에러를 발생시킴
                throw new Error('네트워크 응답이 올바르지 않습니다.');
            }
            return response.json();
        })
        .then(comments => {
            commentsList.innerHTML = ""; // 로딩 메시지 삭제
            if (comments.length === 0) {
                commentsList.innerHTML = "<div>아직 댓글이 없습니다. 첫 번째 댓글을 남겨주세요!</div>";
            } else {
                // 가져온 댓글 목록을 화면에 추가
                comments.forEach(comment => {
                    const p = document.createElement("p");
                    p.textContent = comment.text; // 서버 응답에 'text' 필드가 있다고 가정
                    commentsList.appendChild(p);
                });
            }
        })
        .catch(error => {
            console.error('댓글을 불러오는 데 실패했습니다:', error);
            commentsList.innerHTML = `<p style="color:red;">댓글을 불러오는 중 오류가 발생했습니다.</p>`;
        });

    // 댓글 입력 폼이 없으면 생성
    let commentForm = commentSection.querySelector(".comment-form");
    if (!commentForm) {
        commentForm = document.createElement("div");
        commentForm.classList.add("comment-form");
        commentForm.innerHTML = `
            <textarea id="comment-input-${itemId}" placeholder="댓글을 입력하세요..." style="width:100%; height: 50px;"></textarea>
            <button class="add-comment-btn">댓글 추가</button>
        `;
        commentSection.appendChild(commentForm);

        // 댓글 추가 버튼에 이벤트 리스너 추가
        const addCommentBtn = commentForm.querySelector(".add-comment-btn");
        addCommentBtn.addEventListener("click", () => {
            const commentInput = document.getElementById(`comment-input-${itemId}`);
            const commentText = commentInput.value.trim();
            const loggedIn = document.body.dataset.loggedIn === 'true';

            // 로그인이 되어 있지 않으면 댓글 추가를 막음
            if (!loggedIn) {
                alert("댓글을 추가하려면 먼저 로그인해야 합니다.");
                return;
            }

            if (commentText) {
                // POST 요청으로 서버에 새 댓글을 전송
                fetch(`/comments/add`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        itemId: itemId,
                        text: commentText,
                    }),
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('댓글 저장에 실패했습니다.');
                    }
                    return response.json();
                })
                .then(newComment => {
                    // 성공적으로 저장되면 화면에 새 댓글을 즉시 추가
                    const p = document.createElement("p");
                    p.textContent = newComment.text; // 서버 응답에서 'text' 필드를 사용
                    commentsList.appendChild(p);

                    // 입력 필드 초기화
                    commentInput.value = "";
                })
                .catch(error => {
                    console.error('댓글 저장 중 오류가 발생했습니다:', error);
                    alert("댓글 저장 중 오류가 발생했습니다. 다시 시도해 주세요.");
                });
            }
        });
    }
};