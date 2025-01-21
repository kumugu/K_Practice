import random
import time


def print_with_delay(text, delay=1):
    """텍스트를 일정한 지연 시간 후 출력하는 함수"""
    print(text)
    time.sleep(delay)


def start_game():
    """숫자 추측 게임의 시작을 알리고 범위를 설정하는 함수"""
    print_with_delay("안녕하세요! 숫자 추측 게임에 오신 것을 환영합니다!")
    print_with_delay("제가 생각한 숫자를 맞춰보세요!")
    print_with_delay("게임을 시작하기 전에 범위를 설정해 봅시다.")

    # 사용자로부터 숫자 범위 입력
    while True:
        try:
            min_range = int(input("최소 범위를 입력하세요: "))
            max_range = int(input("최대 범위를 입력하세요: "))
            if min_range >= max_range:
                print("최소 범위는 최대 범위보다 작아야 합니다. 다시 입력하세요.")
                continue
            break
        except ValueError:
            print("유효한 숫자를 입력하세요!")

    return min_range, max_range


def get_user_guess():
    """사용자로부터 숫자 추측값을 입력받는 함수"""
    while True:
        try:
            guess = int(input("추측할 숫자를 입력하세요: "))
            return guess
        except ValueError:
            print("유효한 숫자를 입력하세요!")


def play_game():
    """숫자 추측 게임 실행"""
    # 범위 설정
    min_range, max_range = start_game()

    # 랜덤 숫자 생성
    secret_number = random.randint(min_range, max_range)
    attempts = 0

    print_with_delay(f"숫자를 생각했습니다! {min_range} ~ {max_range} 사이의 숫자입니다.")
    print_with_delay("추측을 시작하세요!")

    while True:
        # 사용자 입력
        user_guess = get_user_guess()
        attempts += 1

        # 추측 결과 확인
        if user_guess < secret_number:
            print_with_delay("너무 낮아요! 다시 시도해보세요.")
        elif user_guess > secret_number:
            print_with_delay("너무 높아요! 다시 시도해보세요.")
        else:
            print_with_delay(f"축하합니다! 정답은 {secret_number}입니다!")
            print_with_delay(f"총 {attempts}번 시도 후 맞추셨습니다!")
            break

    # 게임 종료
    print_with_delay("게임을 종료합니다. 감사합니다!")


if __name__ == "__main__":
    while True:
        play_game()
        again = input("다시 플레이하시겠습니까? (y/n): ").strip().lower()
        if again != 'y':
            print_with_delay("게임을 종료합니다. 다음에 또 만나요!")
            break
