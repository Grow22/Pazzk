const commentButtons = document.querySelectorAll(".comment-btn");

    commentButtons.forEach((button) => {
        button.addEventListener("click", () => {
            const itemId = button.dataset.itemId;
            const commentSection = document.getElementById("comments-" + itemId);

            if (commentSection.style.display === "none" || commentSection.style.display === "") {
                commentSection.style.display = "block";
                loadComments(itemId, commentSection);
            } else {
                commentSection.style.display = "none";
            }
        });
    });

    // 댓글 추가 기능
    const loadComments = (itemId, commentSection) => {
        const commentsList = commentSection.querySelector(".comment-list");
        commentsList.innerHTML = "";

        const allComments = JSON.parse(localStorage.getItem("comments")) || {}; // 객체로 초기화
        const itemComments = allComments[itemId] || [];

        // 댓글들 추가
        itemComments.forEach((comment) => {
            const p = document.createElement("p");
            p.textContent = comment.text;
            commentsList.appendChild(p);
        });

        let commentForm = commentSection.querySelector(".comment-form");
        if (!commentForm) {
            commentForm = document.createElement("div");
            commentForm.classList.add("comment-form");
            commentForm.innerHTML = `
                <textarea id="comment-input-${itemId}" placeholder="댓글을 입력하세요..." style="width:100%; height: 50px;"></textarea>
                <button class="add-comment-btn">댓글 추가</button>
            `;
            commentSection.appendChild(commentForm);

            const addCommentBtn = commentForm.querySelector(".add-comment-btn");
            addCommentBtn.addEventListener("click", () => {
                const commentInput = document.getElementById(`comment-input-${itemId}`);
                const commentText = commentInput.value.trim();

                if (commentText) {
                    const newComment = {
                        id: Date.now(),
                        text: commentText,
                    };

                    // itemId 에 해당하는 댓글에 현재 댓글 저장
                    allComments[itemId] = allComments[itemId] || [];
                    allComments[itemId].push(newComment);
                    localStorage.setItem("comments", JSON.stringify(allComments));

                    // 현재 댓글에 댓글 추가
                    const p = document.createElement("p");
                    p.textContent = newComment.text;
                    commentsList.appendChild(p);

                    commentInput.value = "";
                }
            });
        }
    };