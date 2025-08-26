document.addEventListener('DOMContentLoaded', () => {
    // 모든 비디오 컨테이너를 선택
    const videoContainers = document.querySelectorAll('.video-container');

    // 각 컨테이너에 클릭 이벤트 리스너 추가
    videoContainers.forEach(container => {
        const thumbnail = container.querySelector('.video-thumbnail');
        const playerFrame = container.querySelector('.video-player-frame');

        thumbnail.addEventListener('click', () => {
            // 썸네일 숨기기
            thumbnail.style.display = 'none';

            // 플레이어 보이기
            playerFrame.style.display = 'block';

            // 동영상 자동 재생 (선택 사항)
            // 비디오 ID를 가져와서 iframe의 src를 재설정하여 자동 재생을 트리거
            const videoId = container.dataset.videoId;
            if (videoId) {
                const iframe = playerFrame.querySelector('iframe');
                iframe.src = `https://chzzk.naver.com/embed/clip/${videoId}?autoplay=true`;
            }
        });
    });
});