import random

def guess_number():
    print("숫자 맞추기 게임에 오신 것을 환영합니다!")
    print("1에서 100까지의 숫자 중 하나를 맞춰보세요.")

    number_to_guess = random.randint(1, 100)
    attempts = 0

    while True:
        user_guess = int(input("숫자를 입력하세요: "))
        attempts += 1

        if user_guess < number_to_guess:
            print("더 큰 숫자입니다. 다시 시도하세요!")
        elif user_guess > number_to_guess:
            print("더 작은 숫자입니다. 다시 시도하세요!")
        else:
            print(f"축하합니다! 숫자 {number_to_guess}를 맞추셨습니다!")
            print(f"{attempts}번의 시도 끝에 맞추셨습니다.")
            break

    print("게임이 종료되었습니다. 감사합니다!")

guess_number()
