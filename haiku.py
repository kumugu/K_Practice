import random

def generate_haiku():
    five_syllable_phrases = [
        "An old silent pond", 
        "Autumn moonlight",
        "In the twilight rain",
        "The light of a candle",
        "A world of dew"
    ]
    
    seven_syllable_phrases = [
        "A frog jumps into the pond—",
        "a worm digs silently into the chestnut.",
        "These brilliant-hued hibiscus—",
        "is fluttering, wavering,",
        "and it's drizzling at twilight."
    ]
    
    haiku = []
    haiku.append(random.choice(five_syllable_phrases))
    haiku.append(random.choice(seven_syllable_phrases))
    haiku.append(random.choice(five_syllable_phrases))
    
    return "\n".join(haiku)

if __name__ == "__main__":
    print(generate_haiku())
