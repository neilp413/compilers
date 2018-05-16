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
	sw $v0, ($sp)
	jal procfoo
	# Storing into variable varignore
	la $t0, varignore
	sw $v0, ($t0)
	li $v0 10
	syscall
procfoo:
	li $v0, 0
	#pushes register $v0 onto the stack
	subu $sp, $sp, 4
	sw $v0, ($sp)
	#pushes register $ra onto the stack
	subu $sp, $sp, 4
	sw $ra, ($sp)
	li $v0 1
	#pushes register $v0 onto the stack
	subu $sp, $sp, 4
	sw $v0, ($sp)
	lw $v0, 12($sp)
	#pops register $t0 onto the stack
	lw $t0, ($sp)
	addu $sp, $sp,4
	addu $v0, $t0, $v0
	move $a0 $v0
	li $v0 1
	syscall
	la $a0 newLine
	li $v0 4
	syscall
	#pops register $ra onto the stack
	lw $ra, ($sp)
	addu $sp, $sp,4
	#pops register $v0 onto the stack
	lw $v0, ($sp)
	addu $sp, $sp,4
	#pops register $a0 onto the stack
	lw $a0, ($sp)
	addu $sp, $sp,4
	jr $ra
