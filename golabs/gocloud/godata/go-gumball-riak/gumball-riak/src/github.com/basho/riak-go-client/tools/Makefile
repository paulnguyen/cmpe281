.PHONY: release

release:
ifeq ($(VERSION),)
	$(error VERSION must be set to deploy this code)
endif
ifeq ($(RELEASE_GPG_KEYNAME),)
	$(error RELEASE_GPG_KEYNAME must be set to deploy this code)
endif
	./build/publish $(VERSION) master validate
	git tag --sign -a "$(VERSION)" -m "riak-client-tools $(VERSION)" --local-user "$(RELEASE_GPG_KEYNAME)"
	git push --tags
	./build/publish $(VERSION) master 'Riak Client Tools' 'riak-client-tools'
