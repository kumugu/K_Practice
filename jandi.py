import datetime
import os
import subprocess
import random

# 디렉토리와 파일 생성
def create_daily_file():
    # 날짜별 디렉토리 생성
    today = datetime.datetime.now().strftime('%Y-%m-%d')
    dir_name = f"logs/{today}"
    if not os.path.exists(dir_name):
        os.makedirs(dir_name)

    # 파일 생성
    file_name = f"{dir_name}/log_{today}.txt"
    with open(file_name, "w") as file:
        file.write(f"Log for {today}\n")
        file.write(f"Generated at {datetime.datetime.now().strftime('%H:%M:%S')}\n")
        # 추가 내용: 랜덤한 메시지 작성
        for i in range(1, random.randint(5, 15)):
            file.write(f"Log Entry {i}: This is an automated log entry.\n")
    print(f"File created: {file_name}")
    return file_name

# Git 커밋 및 푸시
def commit_and_push(file_name):
    # Git 명령어 실행
    subprocess.run(["git", "add", file_name])
    commit_message = f"Automated commit for {datetime.datetime.now().strftime('%Y-%m-%d')}"
    subprocess.run(["git", "commit", "-m", commit_message])
    subprocess.run(["git", "push", "origin", "main"])
    print("Changes pushed to GitHub.")

# 메인 함수
def main():
    print("Starting automated GitHub commit...")
    file_name = create_daily_file()
    commit_and_push(file_name)
    print("Automation complete.")

if __name__ == "__main__":
    main()
