<template>
	<div>
		<div v-bind:checked="checked" v-on:change="$emit('change', $event.target.checked)"></div>
		<div>
			头像|时间
		</div>
		<div>
			内容
		</div>
	</div>
</template>

<script>
	export default {
		model: {
			prop: 'checked',
			event: 'change'
		},
		props: {
			checked: Boolean
		},
		name: "ChatText",
		data() {
			return {};
		}
	};
</script>

<style>
</style>
