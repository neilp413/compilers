	#@author Neil Patel
	#@version May 14, 2018
	.data
	newLine: .asciiz "\n"
	varignore: .word 0
	.text
	.globl main
main:
	li $v0 9
	#pushes register $v0 onto the stack
	subu $sp, $sp, 4
	sw$v0, ($sp)
	jal procfoo
	# Storing into variable varignore
	la $t0, varignore
	sw $v0, ($t0)
	li $v0 10
	syscall
