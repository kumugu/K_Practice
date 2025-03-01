class ComplexMath
  def initialize(expression)
    @expression = expression
  end

  def evaluate
    tokens = @expression.split(' ')
    result = tokens[0].to_i

    (1...tokens.size).step(2) do |i|
      operator = tokens[i]
      operand = tokens[i + 1].to_i

      case operator
      when '+'
        result += operand
      when '-'
        result -= operand
      when '*'
        result *= operand
      when '/'
        result /= operand
      else
        raise "Unknown operator: #{operator}"
      end
    end

    result
  end
end

if __FILE__ == $PROGRAM_NAME
  expression = '3 + 5 * 2 - 4 / 2'
  complex_math = ComplexMath.new(expression)
  result = complex_math.evaluate
  puts "Result of '#{expression}' is #{result}"
end
