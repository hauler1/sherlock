# sherlock 通过 Xposed 去 Hook 系统方法实现隐私合规自检

---

## 编译

修改 `SherLockMonitor` 中相关方法，增加自定义的钩子，实现其他项目检测

## 使用

1. 在 `Xposed` `VirtualXposed` `LSPosed` 等类似环境下激活模块
2. 选择生效范围
3. 观察日志

## TODO

```
//TODO 根据检测项目增加方法 Hook
//TODO 增加报告输出与日志本地存储
```
